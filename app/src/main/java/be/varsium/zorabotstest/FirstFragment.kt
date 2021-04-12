package be.varsium.zorabotstest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import be.varsium.AdvancedComposerService
import be.varsium.models.AdvancedComposer
import be.varsium.zorabotstest.databinding.FragmentFirstBinding
import org.koin.android.ext.android.getKoin
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
  private val getModule= getKoin()
    private lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       var modules= (activity as MainActivity).moduleList
        binding.btnAdvancedComposer.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("AdvancedComposer"))
        }
        binding.btnAllBlocks.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("AllBlocks"))
        }
        binding.btnMediaMarkt.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("MediaMarkt"))
        }


        view.findViewById<Button>(R.id.btnAdvanced_composer).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

}