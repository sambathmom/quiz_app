package com.mona.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mona.quizapp.model.TrueFalse;

public class MainActivity extends AppCompatActivity {
    private Button btnTrue;
    private Button btnFalse;
    private TextView tvQuestion;
    private TextView tvScore;
    private ProgressBar pbResult;
    private int index = 0;
    private TrueFalse question;
    private int score = 0;

    private TrueFalse[] questions = new TrueFalse[] {
        new TrueFalse(R.string.question_1, true),
        new TrueFalse(R.string.question_2, false),
        new TrueFalse(R.string.question_3, true),
        new TrueFalse(R.string.question_4, true),
        new TrueFalse(R.string.question_5, false),
        new TrueFalse(R.string.question_6, true),
        new TrueFalse(R.string.question_7, true),
        new TrueFalse(R.string.question_8, false),
        new TrueFalse(R.string.question_9, false),
        new TrueFalse(R.string.question_10, true),
        new TrueFalse(R.string.question_11, true),
        new TrueFalse(R.string.question_12, true),
        new TrueFalse(R.string.question_13, true)
    };

    final int PROGRESS_BAR_VALUE =  (int) Math.ceil(100 / questions.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        btnTrue = (Button) findViewById(R.id.btn_true);
        btnFalse = (Button) findViewById(R.id.btn_false);

        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvScore = (TextView) findViewById(R.id.tv_score);
        pbResult = (ProgressBar) findViewById(R.id.pb_result);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
            index = savedInstanceState.getInt("index");
            tvScore.setText("Score: " + score + "/" + questions.length);
            tvQuestion.setText(questions[index].getQuestionId());

        } else {
            score = 0;
            index = 0;
        }

        question = questions[index];
        tvQuestion.setText(question.getQuestionId());
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        index = index < questions.length -1 ? index + 1 : 0;
        Log.d("Index", "Index " + index);
        if (index == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Game Over");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("The game is over. Your total score is " + score + " out of " + questions.length);
            alertDialog.setPositiveButton("Close dialog", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            alertDialog.show();
        }
        question = questions[index];
        tvQuestion.setText(question.getQuestionId());
        pbResult.incrementProgressBy(PROGRESS_BAR_VALUE );
        tvScore.setText("Score: " + score + "/" + questions.length);
    }

    private void checkAnswer(boolean userSelectAnswer) {
        if (userSelectAnswer == questions[index].isAnswer()) {
            Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_LONG).show();
            score = score + 1;
        } else {
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("score", score);
        outState.putInt("index", index);

    }
}
