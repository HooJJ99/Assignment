package com.example.test.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.Fragment.StartDirections
import com.example.test.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 */
class Start : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentStartBinding>(inflater,
                R.layout.fragment_start,container,false)

        binding.playButton.setOnClickListener { view : View -> view.findNavController().navigate(StartDirections.actionStartToChallenge()) }

        return binding.root
    }


}
