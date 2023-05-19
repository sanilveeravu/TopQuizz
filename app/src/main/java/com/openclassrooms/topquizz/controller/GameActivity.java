package com.openclassrooms.topquizz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.openclassrooms.topquizz.R;
import com.openclassrooms.topquizz.model.Question;
import com.openclassrooms.topquizz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    TextView mGameQuestionTextView;
    Button mGameButton1;
    Button mGameButton2;
    Button mGameButton3;
    Button mGameButton4;

    QuestionBank mQuestionBank = generateQuestions();

    Question mCurrentQuestion;

    int mRemainingQuestionCount;

    int mScore;
    private boolean mEnableTouchEvents;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTION_COUNT,mRemainingQuestionCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mEnableTouchEvents = true;
        mGameQuestionTextView = findViewById(R.id.activity_game_question_text);
        mGameButton1 = findViewById(R.id.activity_game_answer1_btn);
        mGameButton2 = findViewById(R.id.activity_game_answer2_btn);
        mGameButton3 = findViewById(R.id.activity_game_answer3_btn);
        mGameButton4 = findViewById(R.id.activity_game_answer4_btn);
        mGameButton1.setOnClickListener(GameActivity.this);
        mGameButton2.setOnClickListener(GameActivity.this);
        mGameButton3.setOnClickListener(GameActivity.this);
        mGameButton4.setOnClickListener(GameActivity.this);
        if(savedInstanceState!=null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION_COUNT);
        } else {
            mRemainingQuestionCount=4;
            mScore=0;
        }
        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mQuestionBank.getCurrentQuestion());
    }

    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mGameQuestionTextView.setText(question.getQuestion());
        mGameButton1.setText(question.getChoiceList().get(0));
        mGameButton2.setText(question.getChoiceList().get(1));
        mGameButton3.setText(question.getChoiceList().get(2));
        mGameButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who created Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first person land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        Question question4 = new Question("Who won the last soccer world cup?",
                Arrays.asList("Argentina",
                        "Brazil",
                        "Germany",
                        "France"),
                0);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,question4));
    }

    @Override
    public void onClick(View v) {
        int index;
        if (v == mGameButton1) {
            index = 0;
        } else if (v == mGameButton2) {
            index = 1;
        } else if (v == mGameButton3) {
            index = 2;
        } else if (v == mGameButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown View " + v);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
            mScore++;
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_LONG).show();
        }
        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endGame();
                mEnableTouchEvents = true;
            }
        },2000);
    }

    private void endGame() {
        mRemainingQuestionCount--;

        if (mRemainingQuestionCount > 0) {
            mCurrentQuestion = mQuestionBank.getNextQuestion();
            displayQuestion(mCurrentQuestion);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done!")
                    .setMessage("Your score is " + mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(BUNDLE_EXTRA_SCORE,mScore);
                            setResult(RESULT_OK,resultIntent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }
}