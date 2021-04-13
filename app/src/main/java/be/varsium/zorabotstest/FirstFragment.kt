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
        //Safeargs gebruikt + hardcoded omdat het niet zo belangrijk was op welke manier dit op de gui getoond werd. -> dit kan dynamisch gemaakt worden naargelang de knoppen
        //knoppen + directions gaan generene volgens alle R.raw files die er zijn.
        binding.btnAdvancedComposer.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("advanced_composer"))
        }
        binding.btnAllBlocks.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("all_blocks"))
        }
        binding.btnMediaMarkt.setOnClickListener {
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment("mediamarkt_composition"))
        }

        binding.btnUrlJson.setOnClickListener {
            if (!binding.txtUrl.text.isNullOrBlank()){
                findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(binding.txtUrl.text.toString(),true))
            }
        }
    }

}