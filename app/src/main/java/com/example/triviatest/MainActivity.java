package com.example.triviatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triviatest.controller.AppController;
import com.example.triviatest.data.AnswerListAsyncResponse;
import com.example.triviatest.data.QuestionBank;
import com.example.triviatest.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView, questionCounterTextView;
    private Button trueButton, falseButton;
    private ImageButton prevButton, nextButton;
    private int currentQuestionIndex = 0;
    private List<Question> questionList;
    private TextView scoreCard;
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.nextBtn);
        prevButton = findViewById(R.id.prevBtn);
        trueButton = findViewById(R.id.trueBtn);
        falseButton = findViewById(R.id.falseBtn);
        questionCounterTextView = findViewById(R.id.ctrView);
        questionTextView = findViewById(R.id.questionView);
        scoreCard = findViewById(R.id.scoreCard);



        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);


        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                int index = currentQuestionIndex+1;
                questionCounterTextView.setText(index + "/" + questionList.size());
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevBtn:
                if(currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();

                } else {
                    Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nextBtn:
                currentQuestionIndex = (currentQuestionIndex+1)%questionList.size();
                updateQuestion();

                break;
            case R.id.trueBtn:
                checkAnswer(true);
                break;
            case R.id.falseBtn:
                checkAnswer(false);
                break;
        }
    }

    private void checkAnswer(boolean b) {
        boolean check = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(check == b) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            score++;
            scoreCard.setText("Score: "+score );
            currentQuestionIndex = (currentQuestionIndex+1)%questionList.size();
            updateQuestion();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
            currentQuestionIndex = (currentQuestionIndex+1)%questionList.size();
            updateQuestion();
        }

    }


    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(question);
        int index = currentQuestionIndex+1;
        questionCounterTextView.setText(index + "/" + questionList.size());
    }

}