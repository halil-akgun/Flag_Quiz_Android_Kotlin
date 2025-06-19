package com.example.flagquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        Log.d("questions", questions.toString())

        binding.buttonA.setOnClickListener {
            questionCountControl()
        }
    }

    fun loadQuestion() {
        binding.textViewCount.text = "Question ${questionCount + 1}"

        correctOption = questions[questionCount]

        binding.imageViewFlag.setImageResource(
            resources.getIdentifier(
                correctOption.image,
                "drawable",
                packageName
            )
        )

    }

    fun questionCountControl() {
        questionCount++
        if (questionCount == 5) {
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            loadQuestion()
        }
    }
}