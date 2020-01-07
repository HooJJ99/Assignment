package com.example.test.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentResultBinding


/**
 * A simple [Fragment] subclass.
 */
class Result : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentResultBinding>(inflater,
                R.layout.fragment_result,container,false)
        binding.nextBtn.setOnClickListener { view: View ->
            view.findNavController().navigate(ResultDirections.actionResultToChallenge())

        }
        val args = ResultArgs.fromBundle(arguments!!)
        binding.result.text = " ${args.numQuestions} / ${args.numCorrect} "
        return binding.root
    }


}

