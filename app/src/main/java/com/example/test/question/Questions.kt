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
import com.example.test.databinding.FragmentQuestionsBinding


/**
 * A simple [Fragment] subclass.
 */
class Questions : Fragment() {

    var correctIndex=0
    data class EasyQuest(
        val text: String,
        val answers: List<String>)


    private val questions: MutableList<EasyQuest> = mutableListOf(
            EasyQuest(text = "卧",
                    answers = listOf( "wò", "wō","wǒ", "wó")),
            EasyQuest(text = "拟",
                    answers = listOf("nǐ", "nī", "ní", "nì")),
            EasyQuest(text = "它",
                    answers = listOf("tā", "tǎ", "tà", "tá")),
            EasyQuest(text = "得",
                    answers = listOf("dé", "dē", "ddè", "dě")),
            EasyQuest(text = "达",
                    answers = listOf("dá", "dǎ","dà", "dā")),
            EasyQuest(text = "新",
                    answers = listOf("xīn", "xìn", "xǐn", "xín")),
            EasyQuest(text = "事",
                    answers = listOf("shì", "shí", "shǐ", "shī")),
            EasyQuest(text = "闷",
                    answers = listOf("mēn", "měn", "mèn","mén")),
            EasyQuest(text = "夏",
                    answers = listOf("xià", "xiá", "xiā", "xiǎ")),
            EasyQuest(text = "潇",
                    answers = listOf("xiāo", "xiǎo", "xiào", "xiáo"))
    )

    lateinit var currentQuestion: EasyQuest
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 5)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentQuestionsBinding>(inflater,
                R.layout.fragment_questions,container,false)

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
                                .navigate(QuestionsDirections.actionQuestionsToResult(numQuestions, correctIndex))
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
                                .navigate(QuestionsDirections.actionQuestionsToResult(numQuestions, correctIndex))
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
