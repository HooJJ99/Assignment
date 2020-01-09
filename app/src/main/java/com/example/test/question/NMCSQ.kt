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
import com.example.test.databinding.FragmentNmcsqBinding


/**
 * A simple [Fragment] subclass.
 */
class NMCSQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "新___快乐，老师。",
                    answers = listOf("年", "念", "粘", "黏")),
            EasyQuest(text = "你就是传说来的天___的魔鬼。",
                    answers = listOf("堂", "唐", "糖", "躺")),
            EasyQuest(text = "踏上战场的那天就要勇___直前。",
                    answers = listOf("往", "王", "网", "汪")),
            EasyQuest(text = "在一瞬___ 有一百万个可能。",
                    answers = listOf("间", "见", "建", "件")),
            EasyQuest(text = "这冬夜里 有百万个不确___。",
                    answers = listOf("定", "顶", "订", "丁")),
            EasyQuest(text = "在咫尺的未來生___就像茫茫海上一隻小船勇敢乘風破浪。",
                    answers = listOf("活", "火", "或", "货")),
            EasyQuest(text = "白天和晚上都是冬___。",
                    answers = listOf("夜", "也", "页", "叶")),
            EasyQuest(text = "新鲜的水果不断地从国外送进农贸___场。",
                    answers = listOf("市", "是", "事", "实")),
            EasyQuest(text = "這麼年年的兄弟 有誰我更了___你",
                    answers = listOf("解", "姐", "接", "街")),
            EasyQuest(text = "回到十二年前 回___就在眼前",
                    answers = listOf("忆", "一", "已", "以"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNmcsqBinding>(inflater,
                R.layout.fragment_nmcsq,container,false)

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
                                .navigate(NMCSQDirections.actionNMCSQToResult(numQuestions, correctIndex))
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
                                .navigate(NMCSQDirections.actionNMCSQToResult(numQuestions, correctIndex))
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
