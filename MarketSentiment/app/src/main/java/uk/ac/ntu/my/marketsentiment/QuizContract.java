package uk.ac.ntu.my.marketsentiment;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class HeadlinesTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_headlines";
        public static final String COLUMN_HEADLINE = "headline";
        public static final String COLUMN_ANSWER_A_NUMBER = "answer_a_number";
        public static final String COLUMN_ANSWER_B_NUMBER = "answer_b_number";
        public static final String COLUMN_FEEDBACK = "feedback";

    }

}
