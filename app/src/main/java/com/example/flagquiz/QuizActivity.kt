package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.flagquiz.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var questions: ArrayList<Flag>
    private lateinit var wrongOptions: ArrayList<Flag>
    private lateinit var correctOption: Flag
    private lateinit var allOptions: HashSet<Flag>
    private lateinit var dbHelper: DBHelper

    private var questionCount = 0
    private var correctCount = 0
    private var wrongCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DBHelper(this)
        questions = FlagDao().getRandom5Flag(dbHelper)

        loadQuestion()

        binding.buttonA.setOnClickListener {
            checkAnswer(binding.buttonA.text.toString())
            questionCountControl()
        }
        binding.buttonB.setOnClickListener {
            checkAnswer(binding.buttonB.text.toString())
            questionCountControl()
        }
        binding.buttonC.setOnClickListener {
            checkAnswer(binding.buttonC.text.toString())
            questionCountControl()
        }
        binding.buttonD.setOnClickListener {
            checkAnswer(binding.buttonD.text.toString())
            questionCountControl()
        }
    }

    private fun loadQuestion() {
        binding.textViewCount.text = "Question ${questionCount + 1}"

        correctOption = questions[questionCount]

        binding.imageViewFlag.setImageResource(
            resources.getIdentifier(
                correctOption.image,
                "drawable",
                packageName
            )
        )

        wrongOptions = FlagDao().getRandom3WrongFlag(dbHelper, correctOption.id)
        allOptions = HashSet()
        allOptions.add(correctOption)
        allOptions.addAll(wrongOptions)

        binding.buttonA.text = allOptions.elementAt(0).name
        binding.buttonB.text = allOptions.elementAt(1).name
        binding.buttonC.text = allOptions.elementAt(2).name
        binding.buttonD.text = allOptions.elementAt(3).name
    }

    private fun questionCountControl() {
        questionCount++
        if (questionCount == 5) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("correct", correctCount)
            startActivity(intent)
            finish()
        } else {
            loadQuestion()
        }
    }

    // check if the user choose the correct option
    private fun checkAnswer(option: String) {
        if (option == correctOption.name) {
            correctCount++
        } else {
            wrongCount++
        }

        binding.textViewCorrect.text = "Correct: $correctCount"
        binding.textViewWrong.text = "Wrong: $wrongCount"
    }
}