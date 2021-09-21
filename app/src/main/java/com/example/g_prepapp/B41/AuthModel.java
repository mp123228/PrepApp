package com.example.g_prepapp.B41;

public class AuthModel {

    String email;
    String dayname;
    String monthname;
    String date;
    String time;
    String loginout;


    public AuthModel(String email, String dayname, String monthname, String date, String time, String loginout)
    {
        this.email = email;
        this.dayname = dayname;
        this.monthname = monthname;
        this.date = date;
        this.time = time;
        this.loginout = loginout;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public String getMonthname() {
        return monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoginout() {
        return loginout;
    }

    public void setLoginout(String loginout) {
        this.loginout = loginout;
    }


    @Override
    public String toString() {
        return "AuthModel{" +
                "email='" + email + '\'' +
                ", dayname='" + dayname + '\'' +
                ", monthname='" + monthname + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", loginout='" + loginout + '\'' +
                '}';
    }

}

