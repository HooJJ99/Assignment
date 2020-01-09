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
import com.example.test.databinding.FragmentExcsqBinding


/**
 * A simple [Fragment] subclass.
 */
class EXCSQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "我们要去_______健身吗？",
                    answers = listOf("健身房", "操场", "草场", "体育馆")),
            EasyQuest(text = "巴尔扎克笔下的葛朗台,是个________的吝啬鬼。",
                    answers = listOf("爱财如命", "爱不释手", "爱莫能助", "哀兵必胜")),
            EasyQuest(text = "孩子们长大了，已到了______的年龄。",
                    answers = listOf("安家立业", "安如泰山", "安身之处", "安然无恙")),
            EasyQuest(text = "我和他虽是对头,但把他这件事对老板说了,就等于_______,这样的事我不愿做。",
                    answers = listOf("暗箭伤人", "暗无天日", "暗中摸索", "暗黑真经")),
            EasyQuest(text = "姐姐长得漂亮，又有文化，在村里算得上_______的好姑娘。",
                    answers = listOf("百里挑一", "百花齐放", "百炼成钢", "百口莫辩")),
            EasyQuest(text = "知己知彼，才能_______；如果盲人瞎马，横衝直撞，是非常危险的。",
                    answers = listOf("百战百胜", "百依百顺", "百问不厌", "败军之将")),
            EasyQuest(text = "打击敌人要像秋风扫落叶一般_______。",
                    answers = listOf("残酷无情", "惨不忍睹", "残缺不全", "残山剩水")),
            EasyQuest(text = "这两个人性格截然相反，一个能言善辩、巧于词色，一个_______，金口难开。",
                    answers = listOf("沉默寡言", "沉思默想", "趁火打劫", "成年累月")),
            EasyQuest(text = "他只不过取得了一点儿小小的成绩，就开始_______了。",
                    answers = listOf("得意忘形", "得寸进尺", "德高望重", "低三下四")),
            EasyQuest(text = " 二郎神怒斥孙悟空:你偷吃了王母的蟠桃,真是_______。",
                    answers = listOf("胆大包天", "胆小如鼠", "胆小怕事", "单枪匹马"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentExcsqBinding>(inflater,
                R.layout.fragment_excsq,container,false)

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
                                .navigate(EXCSQDirections.actionEXCSQToResult(numQuestions, correctIndex))
                    }
                }else{
                    questionIndex++
                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setTitle("Sorry Wrong Answer !!!")
                    builder.setMessage("Answer : ${currentQuestion.answers[0]}")
                    builder.setNeutralButton("OK"){_,_ ->Toast.makeText(this.context,"Better Luck Next Time.",Toast.LENGTH_SHORT).show()}
                    val dialog: AlertDialog = builder.create()
                    dialog.show()


                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    }else{
                        view.findNavController()
                                .navigate(EXCSQDirections.actionEXCSQToResult(numQuestions, correctIndex))
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
