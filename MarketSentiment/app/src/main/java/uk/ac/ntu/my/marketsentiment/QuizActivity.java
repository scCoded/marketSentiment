package uk.ac.ntu.my.marketsentiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    public static final String SEND_USER = "uk.ac.ntu.my.marketsentiment.SEND_USER";

    public static final long TIMER_EDU = 30000;
    public static final long TIMER_REC = 15000;

    private User officialUser;


    private CountDownTimer timer;
    private TextView textViewHeadline;
    private TextView textViewHeadlineNumber;
    private TextView textViewTimer;
    private RadioGroup rbGroup1;
    private RadioGroup rbGroup2;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton rb6;
    private RadioButton rb7;
    private RadioButton rb8;
    private RadioButton rb9;
    private RadioButton rb10;
    private Button buttonNext;
    private Button buttonFinish;

    private AlertDialog.Builder alert;
    private long timerTimeLeft;
    private Headline currentHeadline;

    private ColorStateList textColorDefaultrbA;
    private ColorStateList textColorDefaultrbB;


    private int headlineCounter;
    private int headlineCountTotal;
    public int score;

    private boolean answered;

    private List<Headline> headlineList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        officialUser = (User)intent.getSerializableExtra(UserActivity.SEND_USER);

        textViewHeadline = findViewById(R.id.textViewHeadline);
        textViewHeadlineNumber = findViewById(R.id.textViewQuestionNumber);
        textViewTimer = findViewById(R.id.textViewTimer);

        rbGroup1 = findViewById(R.id.radioGroup1);
        rbGroup2 = findViewById(R.id.radioGroup2);

        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);
        rb6 = findViewById(R.id.radioButton6);
        rb7 = findViewById(R.id.radioButton7);
        rb8 = findViewById(R.id.radioButton8);
        rb9 = findViewById(R.id.radioButton9);
        rb10 = findViewById(R.id.radioButton10);

        textColorDefaultrbA = rb1.getTextColors();
        textColorDefaultrbB = rb7.getTextColors();

        if(officialUser.getType().equals("recruitment")){

            textViewTimer.setTextColor(Color.RED);
            textViewTimer.setText("15");
        }

        buttonNext = findViewById(R.id.buttonConfirm2);
        buttonFinish = findViewById(R.id.buttonFinish);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishQuiz();
            }
        });

        QuizDbHelper dbHelper = new QuizDbHelper(this);

        headlineList = dbHelper.getAllHeadlines();
        headlineCountTotal = headlineList.size();

        alert = new AlertDialog.Builder(QuizActivity.this);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "Okay.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        showNextHeadline();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!answered) {
                    if ((rb1.isChecked() || rb2.isChecked() || rb3.isChecked()|| rb4.isChecked() || rb5.isChecked() || rb6.isChecked()) && (rb7.isChecked() || rb8.isChecked()|| rb9.isChecked()|| rb10.isChecked())) {
                        checkAnswer();
                    } else {
                        Toast.makeText(getBaseContext(), "Please select an both answers to part A and B.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextHeadline();
                }
            }
        });
    }

    private void showNextHeadline(){

        rb1.setTextColor(textColorDefaultrbA);
        rb2.setTextColor(textColorDefaultrbA);
        rb3.setTextColor(textColorDefaultrbA);
        rb4.setTextColor(textColorDefaultrbA);
        rb5.setTextColor(textColorDefaultrbA);
        rb6.setTextColor(textColorDefaultrbA);

        rb7.setTextColor(textColorDefaultrbB);
        rb8.setTextColor(textColorDefaultrbB);
        rb9.setTextColor(textColorDefaultrbB);
        rb10.setTextColor(textColorDefaultrbB);


        rbGroup1.clearCheck();
        rbGroup2.clearCheck();



        currentHeadline = headlineList.get(headlineCounter);

        if (headlineCounter < headlineCountTotal) {

            textViewHeadline.setText(currentHeadline.getHeadline());

            headlineCounter++;

            textViewHeadlineNumber.setText("Question: " + headlineCounter + " / " + headlineCountTotal);
            answered = false;
            buttonNext.setText("Confirm");

            if(officialUser.getType().equals("recruitment")){
                timerTimeLeft = TIMER_REC;

            }
            else{
                timerTimeLeft = TIMER_EDU;

            }

            startCountDown();

        }

        else {
            finishQuiz();
        }

    }


    private void startCountDown(){
        timer = new CountDownTimer(timerTimeLeft, 1000){
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                textViewTimer.setText("0");
                checkAnswer();

            }
        }.start();

    }



    private void checkAnswer(){

        timer.cancel();

        answered = true;

        RadioButton rbSelectedA = findViewById(rbGroup1.getCheckedRadioButtonId());
        RadioButton rbSelectedB = findViewById(rbGroup2.getCheckedRadioButtonId());

        int answerNumberA = rbGroup1.indexOfChild(rbSelectedA) + 1;
        int answerNumberB = rbGroup2.indexOfChild(rbSelectedB) + 1;

        if (answerNumberA == currentHeadline.getAnswerNumberA()) {
            score += 2;

            if (answerNumberB == currentHeadline.getAnswerNumberB()) {
                score += 1;
            }


        }

        showSolution();

    }

    private void showSolution(){

        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        rb4.setTextColor(Color.RED);
        rb5.setTextColor(Color.RED);
        rb6.setTextColor(Color.RED);
        rb7.setTextColor(Color.RED);
        rb8.setTextColor(Color.RED);
        rb9.setTextColor(Color.RED);
        rb10.setTextColor(Color.RED);

        switch (currentHeadline.getAnswerNumberA()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                break;
            case 4:
                rb4.setTextColor(Color.GREEN);
                break;
            case 5:
                rb5.setTextColor(Color.GREEN);
                break;
            case 6:
                rb6.setTextColor(Color.GREEN);
                break;
        }


        switch (currentHeadline.getAnswerNumberB()){
            case 7:
                rb7.setTextColor(Color.GREEN);
                break;
            case 8:
                rb8.setTextColor(Color.GREEN);
                break;
            case 9:
                rb9.setTextColor(Color.GREEN);
                break;
            case 10:
                rb10.setTextColor(Color.GREEN);
                break;
        }

        if(officialUser.getType().equals("education")){
            alert.setMessage("Feedback: " + currentHeadline.getFeedback());
            AlertDialog feedback = alert.create();
            feedback.show();

        }


        if (headlineCounter < headlineCountTotal) {
            buttonNext.setText("Next");

        }
        else {
            buttonNext.setVisibility(View.INVISIBLE);
            buttonFinish.setVisibility(View.VISIBLE);


        }

    }


    private void finishQuiz() {

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        officialUser.setScore(score);
        intent.putExtra(SEND_USER, officialUser);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(timer!=null){
            timer.cancel();
        }
    }
}

