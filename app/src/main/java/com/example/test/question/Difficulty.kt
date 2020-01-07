package com.example.test.question


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.ModifyUser
import com.example.test.R
import com.example.test.Register
import com.example.test.databinding.FragmentDifficultyBinding
import com.google.firebase.auth.FirebaseAuth

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

        binding.Easy.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToEZPYQ()) }
        binding.Normal.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToQuestions()) }
        binding.Hard.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToHDPYQ()) }
        binding.Extreme.setOnClickListener { view : View -> view.findNavController().navigate(DifficultyDirections.actionDifficultyToEXPYQ()) }


        return binding.root
    }


}
