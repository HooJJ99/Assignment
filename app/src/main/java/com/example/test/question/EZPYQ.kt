package com.example.test.question


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.example.test.R
import com.example.test.databinding.FragmentEzpyqBinding


/**
 * A simple [Fragment] subclass.
 */
class EZPYQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "我",
                    answers = listOf("wǒ", "wō", "wò", "wó")),
            EasyQuest(text = "你",
                    answers = listOf("nǐ", "nī", "ní", "nì")),
            EasyQuest(text = "他",
                    answers = listOf("tā", "tǎ", "tà", "tá")),
            EasyQuest(text = "得",
                    answers = listOf("dé", "dē", "ddè", "dě")),
            EasyQuest(text = "大",
                    answers = listOf("dà", "dǎ", "dá", "dā")),
            EasyQuest(text = "心",
                    answers = listOf("xīn", "xìn", "xǐn", "xín")),
            EasyQuest(text = "是",
                    answers = listOf("shì", "shí", "shǐ", "shī")),
            EasyQuest(text = "门",
                    answers = listOf("mén", "měn", "mèn", "mēn")),
            EasyQuest(text = "下",
                    answers = listOf("xià", "xiá", "xiā", "xiǎ")),
            EasyQuest(text = "小",
                    answers = listOf("xiǎo", "xiāo", "xiào", "xiáo"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentEzpyqBinding>(inflater,
                R.layout.fragment_ezpyq,container,false)

        randomizeQuestions()
        binding.game = this
        binding.nextBtn.setOnClickListener { view:View -> val checkId = binding.questionRadioGroup.checkedRadioButtonId
            if (-1 != checkId) {
                var answerIndex = 0
                when (checkId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    correctIndex++
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {


                        view.findNavController()
                                .navigate(EZPYQDirections.actionEZPYQToResult(numQuestions, correctIndex))
                    }
                }else{
                    questionIndex++
                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setTitle("Sorry Wrong Answer !!!")
                    builder.setMessage("Answer : ${currentQuestion.answers[0]}")
                    builder.setNeutralButton("OK"){_,_ -> Toast.makeText(this.context,"Better Luck Next Time.", Toast.LENGTH_SHORT).show()}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    }else{
                        view.findNavController()
                                .navigate(EZPYQDirections.actionEZPYQToResult(numQuestions, correctIndex))
                    }
                }
            }
        }

        return binding.root
    }
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {

        currentQuestion = questions[questionIndex]

        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }

}
