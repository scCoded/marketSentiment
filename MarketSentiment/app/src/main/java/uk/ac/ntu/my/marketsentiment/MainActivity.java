package uk.ac.ntu.my.marketsentiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SEND_USER = "uk.ac.ntu.my.marketsentiment.SEND_USER";

    EditText usernameBox, passWordBox;

    QuizDbHelper dbHelper = new QuizDbHelper(this);
    private User officialUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameBox = findViewById(R.id.usernameEditText);
        passWordBox = findViewById(R.id.passwordEditText);

        Button buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

    }

    private void checkLogin(){
        String username = usernameBox.getText().toString();
        String password = passWordBox.getText().toString();

        //if valid credentials check mode type

        Boolean check = dbHelper.checkCredentials(username,password);



        if(check){

            officialUser = dbHelper.returnUser(username,password);

            if(officialUser.getType().equals("recruitment") || officialUser.getType().equals("education")){


                Toast.makeText(getBaseContext(), "Login success", Toast.LENGTH_SHORT).show();


                openUserPage();
            }

            else{
                openAdminPage();

            }

        }

        else{

            Toast.makeText(getBaseContext(), "Login fail - please enter correct details", Toast.LENGTH_SHORT).show();
        }

    }


    private void openUserPage() {

        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra(SEND_USER, officialUser);
        startActivity(intent);
    }


    private void openAdminPage() {

        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }
}
