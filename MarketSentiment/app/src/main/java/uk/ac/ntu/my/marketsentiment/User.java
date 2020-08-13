package uk.ac.ntu.my.marketsentiment;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String type;
    private int testTaken;
    private int score;


    public User(){}

    public User(String username, String password, String type, int testTaken, int score) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.testTaken = testTaken;
        this.score = score;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public int getTestTaken() {
        return testTaken;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setTestTaken(int testTaken) {
        this.testTaken = testTaken;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
