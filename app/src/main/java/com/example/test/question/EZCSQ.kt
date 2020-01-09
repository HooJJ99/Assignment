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
import com.example.test.databinding.FragmentEzcsqBinding



/**
 * A simple [Fragment] subclass.
 */
class EZCSQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "我___一起看月亮。",
                    answers = listOf("们", "门", "扪", "钔")),
            EasyQuest(text = "我___你谁跑的最快。",
                    answers = listOf("和", "合", "何", "河")),
            EasyQuest(text = "谁都别___跑。",
                    answers = listOf("想", "向", "像", "现")),
            EasyQuest(text = "小妹___了个玩具回家。",
                    answers = listOf("买", "卖", "麦", "迈")),
            EasyQuest(text = "我的朋友___小心打碎了学校的花盆。",
                    answers = listOf("不", "补", "步", "部")),
            EasyQuest(text = "那女孩___我说，说我是一个小偷。",
                    answers = listOf("对", "队", "堆", "怼")),
            EasyQuest(text = "我飘向北方，___问我家乡。",
                    answers = listOf("别", "憋", "鳖", "瘪")),
            EasyQuest(text = "你___择去崇拜谁呢怨恨谁呢。",
                    answers = listOf("选", "轩", "宣", "萱")),
            EasyQuest(text = "這麼___年的兄弟 有誰我更了解你",
                    answers = listOf("多", "躲", "夺", "朵")),
            EasyQuest(text = "回到___二年前 回憶就在眼前",
                    answers = listOf("十", "是", "事", "时"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentEzcsqBinding>(inflater,
                R.layout.fragment_ezcsq,container,false)

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
                                .navigate(EZCSQDirections.actionEZCSQToResult(numQuestions, correctIndex))
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
                                .navigate(EZCSQDirections.actionEZCSQToResult(numQuestions, correctIndex))
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
