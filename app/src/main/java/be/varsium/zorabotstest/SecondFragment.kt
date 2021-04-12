package be.varsium.zorabotstest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.varsium.DataServiceImpl
import be.varsium.models.AdvancedComposer
import be.varsium.zorabotstest.databinding.FragmentSecondBinding
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.context.KoinContext
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named


class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding

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
         val advancedComposer : DataServiceImpl by inject(named(source))
            Log.d("compo",advancedComposer.Data().toJson())
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }


