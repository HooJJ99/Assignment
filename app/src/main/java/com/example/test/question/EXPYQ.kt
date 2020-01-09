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
import com.example.test.databinding.FragmentExpyqBinding



/**
 * A simple [Fragment] subclass.
 */
class EXPYQ : Fragment() {

    var correctIndex=0
    data class EasyQuest(
            val text: String,
            val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "㙓",
                    answers = listOf("kuí", "kuī", "kuǐ", "kuì")),
            EasyQuest(text = "龘",
                    answers = listOf("dá", "dā", "dǎ", "dà")),
            EasyQuest(text = "翕",
                    answers = listOf("xī", "xì", "xí", "xǐ")),
            EasyQuest(text = "饕",
                    answers = listOf("tāo", "taó", "taò", "taǒ")),
            EasyQuest(text = "呶",
                    answers = listOf("náo", "naō", "naǒ", "naò")),
            EasyQuest(text = "犄",
                    answers = listOf("jī", "jí", "jǐ", "jì")),
            EasyQuest(text = "魑",
                    answers = listOf("chī", "chǐ", "chí", "chì")),
            EasyQuest(text = "靁",
                    answers = listOf("léi", "lèi", "lěi", "lēi")),
            EasyQuest(text = "臧",
                    answers = listOf("zāng", "zǎng", "zàng", "záng")),
            EasyQuest(text = "鼯",
                    answers = listOf("wú", "wū", "wǔ", "wù"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentExpyqBinding>(inflater,
                R.layout.fragment_expyq,container,false)

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
                                .navigate(EXPYQDirections.actionEXPYQToResult(numQuestions, correctIndex))
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
                                .navigate(EXPYQDirections.actionEXPYQToResult(numQuestions, correctIndex))
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
