package com.example.g_prepapp.Learn;

public class LearningModel {

    private String learnId;
    private String learnQuestion;
    private String learnAns;

    public LearningModel(String learnId, String learnQuestion, String learnAns) {
        this.learnId = learnId;
        this.learnQuestion = learnQuestion;
        this.learnAns = learnAns;
    }

    public String getLearnId() {
        return learnId;
    }

    public void setLearnId(String learnId) {
        this.learnId = learnId;
    }

    public String getLearnQuestion() {
        return learnQuestion;
    }

    public void setLearnQuestion(String learnQuestion) {
        this.learnQuestion = learnQuestion;
    }

    public String getLearnAns() {
        return learnAns;
    }

    public void setLearnAns(String learnAns) {
        this.learnAns = learnAns;
    }

    @Override
    public String toString() {
        return "LearningModel{" +
                "learnId='" + learnId + '\'' +
                ", learnQuestion='" + learnQuestion + '\'' +
                ", learnAns='" + learnAns + '\'' +
                '}';
    }
}
