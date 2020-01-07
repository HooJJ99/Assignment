package com.example.test.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.ChallengeDirections
import com.example.test.R
import com.example.test.databinding.FragmentChallengeBinding

/**
 * A simple [Fragment] subclass.
 */
class Challenge : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentChallengeBinding>(inflater,
                R.layout.fragment_challenge,container,false)

        binding.LearnPinYin.setOnClickListener { view : View -> view.findNavController().navigate(ChallengeDirections.actionChallengeToDifficulty()) }
        binding.LearnCS.setOnClickListener { view : View -> view.findNavController().navigate(ChallengeDirections.actionChallengeToDifficulty()) }

        return binding.root
    }


}
