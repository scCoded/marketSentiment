package uk.ac.ntu.my.marketsentiment;
import android.provider.BaseColumns;


public final class UserContract {
    private UserContract(){}

    public static class UsersTable implements BaseColumns{
        public static final String TABLE_NAME = "quiz_users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TEST_TAKEN = "test_taken";
        public static final String COLUMN_SCORE = "score";



    }
}
