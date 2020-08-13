package uk.ac.ntu.my.marketsentiment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    public static final String SEND_USER = "uk.ac.ntu.my.marketsentiment.SEND_USER";

    private AlertDialog.Builder alert;
    private User officialUser;


    private Button recruitmentButton;
    private Button educationButton;
    private TextView welcomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        alert = new AlertDialog.Builder(UserActivity.this);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "Okay.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        Intent intent1 = getIntent();
        officialUser = (User)intent1.getSerializableExtra(MainActivity.SEND_USER);

        welcomeUser = findViewById(R.id.userWelcome);
        welcomeUser.setText("Logged in as: " + officialUser.getUsername());

        recruitmentButton = findViewById(R.id.recruitmentButton);
        educationButton = findViewById(R.id.educationButton);

        if(officialUser.getTestTaken() == 1){
            alert.setMessage("You have already taken this test, to take it again please contact the admin.");
            AlertDialog feedback = alert.create();
            feedback.show();

            recruitmentButton.setEnabled(false);
            educationButton.setEnabled(false);

        }

        if(officialUser.getType().equals("recruitment")){
            educationButton.setEnabled(false);
            alert.setMessage("Information - Applicant: Please select the recruitment test. There are 10 questions. Each question consists of 2 parts and you have 15 seconds to answer each one. Good luck!");
            AlertDialog feedback = alert.create();
            feedback.show();
        }

        if(officialUser.getType().equals("education")){
            recruitmentButton.setEnabled(false);
            alert.setMessage("Information - Intern: Please select the education quiz. There are 10 questions. Each question consists of 2 parts and you have 30 seconds to answer each one. You will also receive an explanation of the answer to each question. Good luck!");
            AlertDialog feedback = alert.create();
            feedback.show();
        }

        recruitmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecruitmentQuiz();
            }
        });

        educationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEducationQuiz();
            }

        });
    }




    private void startRecruitmentQuiz() {


        Intent intent = new Intent(UserActivity.this, QuizActivity.class);
        intent.putExtra(SEND_USER, officialUser);
        startActivity(intent);
        finish();
    }


    private void startEducationQuiz() {

        Intent intent = new Intent(UserActivity.this, QuizActivity.class);
        intent.putExtra(SEND_USER, officialUser);
        startActivity(intent);
        finish();
    }
}
