package com.example.g_prepapp.History;

public class HistoryModel
{

    private String uid;
    private String mode;
    private String category;
    private String skip;
    private String score;
    private String date;

    public HistoryModel(String uid, String mode, String category, String skip, String score, String date) {
        this.uid = uid;
        this.mode = mode;
        this.category = category;
        this.skip = skip;
        this.score = score;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSkip() {
        return skip;
    }

    public void setSkip(String skip) {
        this.skip = skip;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "uid='" + uid + '\'' +
                ", mode='" + mode + '\'' +
                ", category='" + category + '\'' +
                ", skip='" + skip + '\'' +
                ", score='" + score + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
