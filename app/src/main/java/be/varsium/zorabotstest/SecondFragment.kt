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
import be.varsium.models.AdvancedComposer
import be.varsium.zorabotstest.databinding.FragmentSecondBinding
import org.koin.android.ext.android.inject


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

            view.findViewById<Button>(R.id.button_second).setOnClickListener {
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        val test :AdvancedComposer by inject()
        Log.d("",test.toString())
        }
    }


