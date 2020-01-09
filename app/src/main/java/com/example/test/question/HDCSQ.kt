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
import com.example.test.databinding.FragmentHdcsqBinding


/**
 * A simple [Fragment] subclass.
 */
class HDCSQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "他孤苦伶仃，只有老鼠，蝙蝠和飞蛾与他相___。",
                    answers = listOf("伴", "板", "版", "办")),
            EasyQuest(text = "但是他欢天喜地地为自己辩解，简直像精___错乱了一样。",
                    answers = listOf("神", "深", "沈", "身")),
            EasyQuest(text = "商店___偿了顾客的经济损失,赢得了信誉。",
                    answers = listOf("补", "部", "步", "布")),
            EasyQuest(text = "武则天视太平如掌上明珠，对这个她唯一存___的女儿，她有着一种难以言表的补偿心理。",
                    answers = listOf("活", "火", "货", "获")),
            EasyQuest(text = "战___活活拆散了他一家八口。",
                    answers = listOf("争", "真", "朕", "镇")),
            EasyQuest(text = "他懊___当初没有听从大家的劝告。",
                    answers = listOf("悔", "惠", "慧", "辉")),
            EasyQuest(text = " 暮色___之中,远处的钓鱼城更增添了一层历史凝重感。",
                    answers = listOf("苍茫", "藏满", "苍莽", "参忙")),
            EasyQuest(text = "小王自以为是,还沾沾自喜,不知___的人,是从来不自以为了不起的。",
                    answers = listOf("博学", "剥削", "勃学", "白雪")),
            EasyQuest(text = "对待像狼一样的恶人，一定要给予严厉的___，决不能心慈手软。",
                    answers = listOf("惩罚", "乘法", "成发", "城发")),
            EasyQuest(text = "韩信忍住了一时的___，终成一代名将。",
                    answers = listOf("耻辱", "赤露", "车路", "磁路"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHdcsqBinding>(inflater,
                R.layout.fragment_hdcsq,container,false)

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
                                .navigate(HDCSQDirections.actionHDCSQToResult(numQuestions, correctIndex))
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
                                .navigate(HDCSQDirections.actionHDCSQToResult(numQuestions, correctIndex))
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
