package uk.ac.ntu.my.marketsentiment;

public class Headline {
    private String headline;
    private int answerNumberA;
    private int answerNumberB;
    private String feedback;

    public Headline(){}

    public Headline(String headline, int answerNumberA, int answerNumberB, String feedback) {
        this.headline = headline;
        this.answerNumberA = answerNumberA;
        this.answerNumberB = answerNumberB;
        this.feedback = feedback;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public int getAnswerNumberA() {
        return answerNumberA;
    }

    public void setAnswerNumberA(int answerNumberA) {
        this.answerNumberA = answerNumberA;
    }

    public int getAnswerNumberB() {
        return answerNumberB;
    }

    public void setAnswerNumberB(int answerNumberB) {
        this.answerNumberB = answerNumberB;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
