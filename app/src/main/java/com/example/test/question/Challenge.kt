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
import com.example.test.databinding.FragmentChallengeBinding
import com.google.firebase.auth.FirebaseAuth

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
        binding.LearnCS.setOnClickListener { view : View -> view.findNavController().navigate(ChallengeDirections.actionChallengeToDifficulty2()) }

        return binding.root
    }


}
