package be.varsium.zorabotstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.fragment.findNavController
import be.varsium.DataServiceImpl
import be.varsium.models.AdvancedComposer
import be.varsium.network.ZorabotsApi
import be.varsium.zorabotstest.databinding.FragmentSecondBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
private lateinit var scope:LifecycleCoroutineScope
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
val source =SecondFragmentArgs.fromBundle(requireArguments()).Source
        val urlSource= SecondFragmentArgs.fromBundle(requireArguments()).UrlSource
        if (!urlSource){
         val advancedComposer : DataServiceImpl by inject(named(source))}

        else{
            var advancedComposer:AdvancedComposer
        scope.launch(Dispatchers.IO){
                advancedComposer = ZorabotsApi(source).zoraBotsApiService.getJsonsAsync().await()
        }

        }
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }


