package com.mi.quiz_app;

import android.credentials.CreateCredentialException;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import color.WHITE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questionTextview;
    TextView totalquestionsTextview;
    Button ansA, ansB, ansC, ansD;
    Button Submit;

    int Score = 0;
    int totalQuestions = QuestionAnswers.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswers = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalquestionsTextview = findViewById(R.id.total_question);
        questionTextview = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansD = findViewById(R.id.ans_d);
        Submit = findViewById(R.id.btn_submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        Submit.setOnClickListener(this);



        totalquestionsTextview.setText("Total questions" + totalQuestions);
        loadnewQuestion();


    }

    private void loadnewQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz();
            return;
        }
        questionTextview.setText(QuestionAnswers.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswers.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswers.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswers.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswers.choices[currentQuestionIndex][3]);

        selectedAnswers = "";
    }

    private void finishQuiz() {
        String passStatus;
        if (Score >= totalQuestions * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("your score is :"+Score+"Out of "+totalQuestions)
                .setPositiveButton("Restart",((dialog, i) -> restartQuiz()))
                .setCancelable(false)
                .show();
    }
    private void restartQuiz(){
        Score = 0;
        currentQuestionIndex = 0;
        loadnewQuestion();
    }
    @Override
    public void onClick(View view){
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.btn_submit) {
            if (!selectedAnswers.isEmpty()) {
                if (selectedAnswers.equals(QuestionAnswers.correctAnswers[currentQuestionIndex])) {
                    Score++;  // Increment score correctly
                } else {
                    clickedButton.setBackgroundColor(Color.MAGENTA);
                }
                currentQuestionIndex++;
                loadnewQuestion();
            } else {
                // Handle case where no answer is selected (optional)
            }
        } else {
            selectedAnswers = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.YELLOW);
        }
    }
}

