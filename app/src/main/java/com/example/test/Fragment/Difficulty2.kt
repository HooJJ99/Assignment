package com.example.test.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.Fragment.Difficulty2Directions
import com.example.test.R
import com.example.test.databinding.FragmentDifficulty2Binding
import com.example.test.databinding.FragmentDifficultyBinding
import kotlinx.android.synthetic.main.fragment_difficulty.*

/**
 * A simple [Fragment] subclass.
 */
class Difficulty2 : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDifficulty2Binding>(inflater,
                R.layout.fragment_difficulty2,container,false)

        binding.Easy.setOnClickListener { view : View -> view.findNavController().navigate(Difficulty2Directions.actionDifficulty2ToEZCSQ()) }
        binding.Normal.setOnClickListener { view : View -> view.findNavController().navigate(Difficulty2Directions.actionDifficulty2ToNMCSQ()) }
        binding.Hard.setOnClickListener { view : View -> view.findNavController().navigate(Difficulty2Directions.actionDifficulty2ToHDCSQ()) }
        binding.Extreme.setOnClickListener { view : View -> view.findNavController().navigate(Difficulty2Directions.actionDifficulty2ToEXCSQ()) }


        return binding.root
    }


}
