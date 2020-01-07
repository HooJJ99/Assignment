package com.example.test


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.databinding.FragmentDifficultyBinding
import kotlinx.android.synthetic.main.fragment_difficulty.*

/**
 * A simple [Fragment] subclass.
 */
class Difficulty : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentDifficultyBinding>(inflater,
            R.layout.fragment_difficulty,container,false)

        binding.Easy.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToQuestions()) }
        binding.Normal.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToQuestions()) }
        binding.Hard.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToQuestions()) }
        binding.Extreme.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToQuestions()) }

        return binding.root
    }


}
