package com.example.g_prepapp;

public class ModelLearning {

    int id;
    String category;
    String question;
    String ans;

    public ModelLearning(int id,String category, String question, String ans) {
        this.id=id;
        this.category = category;
        this.question = question;
        this.ans = ans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "ModelLearning{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", question='" + question + '\'' +
                ", ans='" + ans + '\'' +
                '}';
    }
}
