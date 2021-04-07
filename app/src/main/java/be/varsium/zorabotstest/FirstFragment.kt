package be.varsium.zorabotstest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import be.varsium.Advanced_composerService
import be.varsium.DataServiceImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val advancedComposerModule = module {
            single { DataServiceImpl(get()) as Advanced_composerService }
        }
        startKoin {
            modules(advancedComposerModule)
        }

        view.findViewById<Button>(R.id.btnAdvanced_composer).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}