package be.varsium.zorabotstest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import be.varsium.DataServiceImpl
import be.varsium.models.AdvancedComposer
import be.varsium.models.BlockInfo
import be.varsium.models.TimelineEntry
import be.varsium.network.ZorabotsApi
import be.varsium.zorabotstest.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties


class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private var parsingSuccesFull = true
    private lateinit var advancedComposer: AdvancedComposer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var source = SecondFragmentArgs.fromBundle(requireArguments()).Source
        val urlSource = SecondFragmentArgs.fromBundle(requireArguments()).UrlSource
        if (!urlSource) {
            val dataservice: DataServiceImpl by inject(named(source))
            advancedComposer = dataservice.Data();
            createGuiWithComposer(advancedComposer)

        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                if (source.substring(source.length - 1, source.length) != "/") {
                    source = "$source/"
                }
                var response = ZorabotsApi(source).zoraBotsApiService.getJsonsAsync().await()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        advancedComposer = body
                    }
                }
            }.invokeOnCompletion {
                if (this::advancedComposer.isInitialized) {
                    createGuiWithComposer(advancedComposer)
                }
            }

        }
        view.findViewById<Button>(R.id.btnMainPage).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }


//Everything for Dynamic made Gui
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private fun createGuiWithComposer(advancedComposer: AdvancedComposer) {
        for (variable in AdvancedComposer::class.memberProperties) {
            if (variable.getValue(advancedComposer, variable).toString() != "null") {
                if (variable.name != "timelineEntries") {
                    val layout = LinearLayout(activity)
                    layout.addView(
                        createHorizontalViewAdvancedComposer(
                            advancedComposer,
                            variable
                        )
                    )
                    binding.cardLinear.addView(layout)
                } else if (variable.name == "timelineEntries") {
                    for (timeline in (variable.getValue(
                        advancedComposer,
                        variable
                    ) as ArrayList<TimelineEntry>)) {
                        createTimeLineEntry(timeline)
                    }
                }
            }
        }
    }

    private fun createHorizontalViewAdvancedComposer(
        advancedComposer: AdvancedComposer,
        variable: KProperty1<AdvancedComposer, *>
    ): LinearLayout {
        val text = TextView(activity)
        text.textSize = 15F
        val label = TextView(activity)
        label.textSize = 21F
        val horizontalView = LinearLayout(activity)
        horizontalView.orientation = LinearLayout.HORIZONTAL
        label.text = variable.name + ": "
        text.text = variable.getValue(advancedComposer, variable).toString()
        horizontalView.addView(label)
        horizontalView.addView(text)
        return horizontalView
    }

    private fun createHorizontalViewBlockInfo(
        blockInfo: BlockInfo,
        variable: KProperty1<BlockInfo, *>
    ): LinearLayout {
        val text = TextView(activity)
        text.textSize = 15F
        val label = TextView(activity)
        label.textSize = 21F
        val horizontalView = LinearLayout(activity)
        horizontalView.orientation = LinearLayout.HORIZONTAL
        label.text = variable.name + ": "
        text.text = variable.getValue(blockInfo, variable).toString()
        horizontalView.addView(label)
        horizontalView.addView(text)
        return horizontalView
    }

    private fun createHorizontalViewTimeLine(
        timeline: TimelineEntry,
        variable: KProperty1<TimelineEntry, *>
    ): LinearLayout {
        val text = TextView(activity)
        text.textSize = 15F
        val label = TextView(activity)
        label.textSize = 21F
        val horizontalView = LinearLayout(activity)
        horizontalView.orientation = LinearLayout.HORIZONTAL
        label.text = variable.name + ": "
        text.text = variable.getValue(timeline, variable).toString()
        horizontalView.addView(label)
        horizontalView.addView(text)
        return horizontalView
    }

    private fun createTimeLineEntry(timeline: TimelineEntry) {
        val text = TextView(activity)
        text.textSize = 15F
        val label = TextView(activity)
        label.textSize = 21F
        val verticalView = LinearLayout(activity)
        verticalView.orientation = LinearLayout.VERTICAL
        verticalView.setBackgroundResource(R.drawable.customborder)

        for (timeVariable in TimelineEntry::class.memberProperties) {
            if (timeVariable.getValue(timeline, timeVariable).toString() != "null") {
                if (timeVariable.name == "blockInfo") {
                    verticalView.addView(createBlocks(timeline.blockInfo))
                } else {
                    verticalView.addView(createHorizontalViewTimeLine(timeline, timeVariable))
                }
            }
        }
        binding.VerticalView.addView(verticalView)
    }

    private fun createBlocks(blockInfo: BlockInfo): LinearLayout {
        val verticalView = LinearLayout(activity)
        verticalView.orientation = LinearLayout.VERTICAL
        for (blockVariable in BlockInfo::class.memberProperties) {
            if (blockVariable.getValue(
                    blockInfo,
                    blockVariable
                ).toString() != "null"
            ) {
                verticalView.addView(
                    createHorizontalViewBlockInfo(
                        blockInfo,
                        blockVariable
                    )
                )
            }
        }
        return verticalView
    }
}


