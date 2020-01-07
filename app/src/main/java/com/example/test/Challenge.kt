package com.example.test


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.databinding.FragmentChallengeBinding
import kotlinx.android.synthetic.main.fragment_challenge.*

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
        val py = LearnPinYin.toString()
        val cs = LearnCS.toString()
        binding.LearnPinYin.setOnClickListener { view : View -> view.findNavController().navigate(ChallengeDirections.actionChallengeToDifficulty(py)) }
        binding.LearnCS.setOnClickListener { view : View -> view.findNavController().navigate(ChallengeDirections.actionChallengeToDifficulty(cs)) }

        return binding.root
    }


}
