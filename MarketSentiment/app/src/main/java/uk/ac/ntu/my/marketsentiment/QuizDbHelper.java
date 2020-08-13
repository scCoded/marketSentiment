package uk.ac.ntu.my.marketsentiment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MarketSentiment.db";
    private static final int DATABASE_VERSION = 13;

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_HEADLINE_TABLE = "CREATE TABLE " +
                QuizContract.HeadlinesTable.TABLE_NAME + "(" +
                QuizContract.HeadlinesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.HeadlinesTable.COLUMN_HEADLINE + " TEXT, " +
                QuizContract.HeadlinesTable.COLUMN_ANSWER_A_NUMBER + " INTEGER, " +
                QuizContract.HeadlinesTable.COLUMN_ANSWER_B_NUMBER + " INTEGER," +
                QuizContract.HeadlinesTable.COLUMN_FEEDBACK + " TEXT" +
                ")";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " +
                UserContract.UsersTable.TABLE_NAME + "(" +
                UserContract.UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserContract.UsersTable.COLUMN_USERNAME + " TEXT, " +
                UserContract.UsersTable.COLUMN_PASSWORD + " TEXT, " +
                UserContract.UsersTable.COLUMN_TYPE + " TEXT," +
                UserContract.UsersTable.COLUMN_TEST_TAKEN + " INTEGER," +
                UserContract.UsersTable.COLUMN_SCORE + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_HEADLINE_TABLE);
        fillHeadlinesTable();

        db.execSQL(SQL_CREATE_USER_TABLE);
        fillUsersTable();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizContract.HeadlinesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.UsersTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillHeadlinesTable(){
        Headline h1 = new Headline("OPEC expects a cut of 9mm/day", 1,7,"Cutbacks tend to indicate that supply is higher than demand, and hence the oil prices fall. ");
        addHeadline(h1);
        Headline h2 = new Headline("Result from OPEC meeting is a cut of 13mm/day", 1,7,"The actual result is a lot worse than expected.");
        addHeadline(h2);
        Headline h3 = new Headline("'Covid-19 rates fall in central Europe, whilst struggles in the US continue'. Which index will respond positively to this?", 5,7,"This shows promise about future economic re-openings. It shows a negative condition in America so will harm the S&P 500, whilst we do not know the impact on the Hang Seng this headline will have if any.");
        addHeadline(h3);
        Headline h4 = new Headline( "China announces $200Bn worth of tariffs on the US",2,9,"This is because demand for currencies is dependent on value of exports from the domestic country (in this case, the U.S.). China imposing tariffs will reduce American exports to China, lowering demand for American goods, and by proxy the Dollar. This will cause a weakening (depreciation) of the currency.");
        addHeadline(h4);
        Headline h5 = new Headline("Brexit talks break down, Boris tells EU no more negotiation.",4,9,"Trade is good for economies, especially big firms such as those represented in the FTSE 100. A breakdown in talks means that a hard Brexit is now very likely, which in turn will dampen trade and reduce large firm revenues.");
        addHeadline(h5);
        Headline h6 = new Headline("Unemployment levels have stabilised, but wage growth has accelerated stoking inflation fears.",6,7," Gold is typically seen as an inflation hedge, the more inflation the is, the higher the price of gold will likely gold, this is due to the general fixed nature of the asset. The effect on Oil is negligible.");
        addHeadline(h6);
        Headline h7 = new Headline("US government announces plans for increased fracking in the Nevada desert.", 1,9,"This is because hydraulic fracking produces gas which is a substitute for oil. The more gas there is, the lower the gas price, and thus the lower demand there will be for oil and so the price will fall.");
        addHeadline(h7);
        Headline h8 = new Headline("UK in talks with Australia and Canada about reduced tariffs on minerals post Brexit.",4,7," This is because such a deal were it to go ahead will reduce cost of inputs for UK manufacturing firms, which will subdue inflation, increase profits, and promote economic activity.");
        addHeadline(h8);
        Headline h9 = new Headline("Trump announces a new corporate tax bill which will see a large reduction in federal rates.",2,7,"Lower corporate taxes means more profits for big businesses which makes them more valuable.");
        addHeadline(h9);
        Headline h10 = new Headline("Alphabet forced to 'break-up' over new anti-trust bill. Government officials announce this is the first of many new movements to introduce greater competition into the market.",3,9,"The NASDAQ is the American tech stock index. New anti-trust regulation will cause the break-up of many big tech names, this will cause large uncertainty for future tech stocks and mean that investors are likely to 'flee' the market.");
        addHeadline(h10);


    }

    public void addHeadline(Headline headline){

        ContentValues cv = new ContentValues();
        cv.put(QuizContract.HeadlinesTable.COLUMN_HEADLINE, headline.getHeadline());
        cv.put(QuizContract.HeadlinesTable.COLUMN_ANSWER_A_NUMBER, headline.getAnswerNumberA());
        cv.put(QuizContract.HeadlinesTable.COLUMN_ANSWER_B_NUMBER, headline.getAnswerNumberB());
        cv.put(QuizContract.HeadlinesTable.COLUMN_FEEDBACK, headline.getFeedback());
        db.insert(QuizContract.HeadlinesTable.TABLE_NAME,null, cv);

    }


    public List<Headline> getAllHeadlines(){
        List<Headline> headlineList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizContract.HeadlinesTable.TABLE_NAME, null);

            if(c.moveToFirst()) {
                do {

                    Headline headline = new Headline();
                    headline.setHeadline(c.getString(c.getColumnIndex(QuizContract.HeadlinesTable.COLUMN_HEADLINE)));
                    headline.setAnswerNumberA(c.getInt(c.getColumnIndex(QuizContract.HeadlinesTable.COLUMN_ANSWER_A_NUMBER)));
                    headline.setAnswerNumberB(c.getInt(c.getColumnIndex(QuizContract.HeadlinesTable.COLUMN_ANSWER_B_NUMBER)));
                    headline.setFeedback(c.getString(c.getColumnIndex(QuizContract.HeadlinesTable.COLUMN_FEEDBACK)));

                    headlineList.add(headline);

                } while (c.moveToNext());
            }

        c.close();
        return headlineList;
    }


    private void fillUsersTable(){
        User u1 = new User("sophie","pass","admin",0, 0);
        addUser(u1);

        User u2 = new User("intern1","pass","education",0, 0);
        addUser(u2);

        User u3 = new User("applicant1","pass","recruitment",1, 15);
        addUser(u3);
    }

    public void addUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UsersTable.COLUMN_USERNAME, user.getUsername());
        cv.put(UserContract.UsersTable.COLUMN_PASSWORD, user.getPassword());
        cv.put(UserContract.UsersTable.COLUMN_TYPE, user.getType());
        cv.put(UserContract.UsersTable.COLUMN_TEST_TAKEN, user.getTestTaken());
        cv.put(UserContract.UsersTable.COLUMN_SCORE, user.getScore());
        db.insert(UserContract.UsersTable.TABLE_NAME,null, cv);
    }


    public boolean checkCredentials(String username, String password){
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UserContract.UsersTable.TABLE_NAME + " WHERE username=? AND password=?",new String[]{username,password});

        if(c.getCount()>0){
            return true;
        }
        else{
            return false;
        }

    }

    public User returnUser(String username, String password) {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + UserContract.UsersTable.TABLE_NAME + " WHERE username=? AND password=?", new String[]{username, password});

        User user = new User();
        if (c.moveToLast()) {
            user.setUsername(c.getString(c.getColumnIndex(UserContract.UsersTable.COLUMN_USERNAME)));
            user.setPassword(c.getString(c.getColumnIndex(UserContract.UsersTable.COLUMN_PASSWORD)));
            user.setType(c.getString(c.getColumnIndex(UserContract.UsersTable.COLUMN_TYPE)));
            user.setTestTaken(c.getInt(c.getColumnIndex(UserContract.UsersTable.COLUMN_TEST_TAKEN)));
            user.setScore(c.getInt(c.getColumnIndex(UserContract.UsersTable.COLUMN_SCORE)));

            return user;

        }
        else {
            Log.e("error not found", "user can't be found or database empty");
            return user;
        }


    }

    public long getUserIndex(String username, String password){
        db = getReadableDatabase();
        long index;
        Cursor c = db.rawQuery("SELECT * FROM " + UserContract.UsersTable.TABLE_NAME + " WHERE username=? AND password=?", new String[]{username, password});
        if (c.moveToLast()) {
            index = c.getLong(c.getColumnIndex(UserContract.UsersTable._ID));
            return index;

        }

        else{
            index = -1;
            return index;
        }



    }

    public void updateScore(User user, long id){
        ContentValues cv = new ContentValues();
        cv.put(UserContract.UsersTable.COLUMN_USERNAME, user.getUsername());
        cv.put(UserContract.UsersTable.COLUMN_PASSWORD, user.getPassword());
        cv.put(UserContract.UsersTable.COLUMN_TYPE, user.getType());
        cv.put(UserContract.UsersTable.COLUMN_TEST_TAKEN, user.getTestTaken());
        cv.put(UserContract.UsersTable.COLUMN_SCORE, user.getScore());

        db.update(UserContract.UsersTable.TABLE_NAME,cv, "_id="+id, null);

    }


    public Cursor getUserData(){
        db = getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + UserContract.UsersTable.TABLE_NAME,null);
        return data;

    }

}
