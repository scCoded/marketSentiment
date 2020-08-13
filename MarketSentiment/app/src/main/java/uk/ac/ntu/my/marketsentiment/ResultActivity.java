package uk.ac.ntu.my.marketsentiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    QuizDbHelper dbHelper = new QuizDbHelper(this);

    private User officialUser;
    private TextView goodbyeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Intent intent = getIntent();
        officialUser = (User)intent.getSerializableExtra(QuizActivity.SEND_USER);
        officialUser.setTestTaken(1);

        goodbyeUser = findViewById(R.id.userGoodbye);

        goodbyeUser.setText("Logged in as: " + officialUser.getUsername());

        TextView textViewScore = findViewById(R.id.ScoreTextView);
        textViewScore.setText("Your final score was: " + officialUser.getScore() + " / 30" );

        long userIndex = dbHelper.getUserIndex(officialUser.getUsername(),officialUser.getPassword());

        dbHelper.updateScore(officialUser,userIndex);

    }

    private void logout(){
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
