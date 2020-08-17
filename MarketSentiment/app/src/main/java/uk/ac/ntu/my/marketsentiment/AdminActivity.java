package uk.ac.ntu.my.marketsentiment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    Button logout2, reset;
    ListView listView;
    ArrayList<User> userList;
    QuizDbHelper dbHelper;
    User user, userToUpdate;
    private EditText usernameBox, passwordBox;

    Cursor c;
    int row_amount;

    FiveColumnListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbHelper = new QuizDbHelper(this);


        usernameBox = findViewById(R.id.usernameEditText2);
        passwordBox = findViewById(R.id.passwordEditText2);


        loadTable();





        logout2 = findViewById(R.id.logoutButton2);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        reset = findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDetails();
            }
        });



    }

    public void loadTable(){

        userList = new ArrayList<>();
        c = dbHelper.getUserData();
        row_amount = c.getCount();


        if(row_amount == 0){
            Toast.makeText(getBaseContext(), "No Users Found - Empty Database", Toast.LENGTH_SHORT).show();
        }

        else{

            while(c.moveToNext()){
                user = new User(c.getString(1), c.getString(2), c.getString(3), c.getInt(4),c.getInt(5));
                userList.add(user);
            }
            adapter = new FiveColumnListAdapter(this,R.layout.list_adapter, userList);
            listView = (ListView) findViewById(R.id.ListViewDb);
            listView.setAdapter(adapter);
        }
    }

    private void resetDetails(){

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        Boolean check = dbHelper.checkCredentials(username,password);
        long index;


        if(check){
            userToUpdate = dbHelper.returnUser(username,password);

            index = dbHelper.getUserIndex(username,password);


            userToUpdate.setPassword("pass");
            userToUpdate.setTestTaken(0);
            userToUpdate.setScore(0);

            dbHelper.updateScore(userToUpdate,index);

            Toast.makeText(getBaseContext(), "Updated user: "+ username, Toast.LENGTH_SHORT).show();

            adapter.clear();
            adapter.notifyDataSetChanged();
            loadTable();

        }

        else{
            Toast.makeText(getBaseContext(), "Error - Could not find user.", Toast.LENGTH_SHORT).show();


        }


    }

    private void logout(){
        Intent intent = new Intent(AdminActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
