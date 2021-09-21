package com.example.g_prepapp.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g_prepapp.Category.MainRoomActivity;
import com.example.g_prepapp.Common.Util;
import com.example.g_prepapp.History.HistoryActivity;
import com.example.g_prepapp.JSONParser;
import com.example.g_prepapp.MainActivity;
import com.example.g_prepapp.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {


    JSONParser json=new JSONParser();
    ArrayList<QuestionsModel> questions =new ArrayList<>();
    ArrayList<QuestionsModel> questionsModel1 =new ArrayList<>();
    JSONParser jsonp=new JSONParser();
    ProgressDialog pdialog;
    private QuestionsModel currentQuestion;
    private int score=0,skip=0,cnt=0;
    long backPresedTime;

    JSONArray array=null;
    private static final String KEY_QUESTION_LIST="keyQuestionList";
    String question="",option1="",option2="",option3="",option4="",questionCat="",question_mode="";
     int ans=0;
     int questionCounter=0;
     int totalquestioncount;
    String mode="",category="";
    TextView textViewCountdown;
    CountDownTimer countDownTimer;
    public static final long COUNTDOWN_IN_MILLIS=900000;
    long timeLeftInMillis;
    ColorStateList textcolorDefaultRb;
    ColorStateList textColorDefaultCd;
    TextView textViewQuestion,textViewQuestionCount,textviewMode,textviewCategory;
    RadioGroup rbGroup;
    RadioButton rb1,rb2,rb3,rb4;
    Button btnconfirm,btnskip;
    boolean answered;
    SharedPreferences ss;
    public static final String session_data="data";
    String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ss=getSharedPreferences(session_data,MODE_PRIVATE);

        uid=ss.getString("uid",null);

        textviewMode=findViewById(R.id.text_view_difficulty);
        textviewCategory=findViewById(R.id.text_view_category);
        textViewQuestionCount=findViewById(R.id.text_view_question_count);
        textViewQuestion=findViewById(R.id.text_view_question);
        rbGroup=findViewById(R.id.radio_groud);
        rb1=findViewById(R.id.radio_button1);
        rb2=findViewById(R.id.radio_button2);
        rb3=findViewById(R.id.radio_button3);
        rb4=findViewById(R.id.radio_button4);
        btnconfirm=findViewById(R.id.button_confirm_text);
        btnskip=findViewById(R.id.button_skip_text);

        textcolorDefaultRb=rb1.getTextColors();

        textViewCountdown=findViewById(R.id.text_view_countdown);
        timeLeftInMillis=COUNTDOWN_IN_MILLIS;

        Intent intent=getIntent();

        if(intent != null)
        {

            mode=intent.getStringExtra("mode");
            category=intent.getStringExtra("category");
            textviewMode.setText("Mode:"+mode);
            textviewCategory.setText("Category:"+category);

        }
        else
        {
            Toast.makeText(this, "mode and category data not  available", Toast.LENGTH_SHORT).show();
        }

            new fetch_question().execute();
            Log.e("toalquestioncount=",totalquestioncount+"");

            btnskip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    skip++;
                    showNextQuestion();

                }
            });

            btnconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!answered)
                    {
                        if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked())
                        {
                            checkAnswer();
                        }
                        else
                        {
                            Toast.makeText(QuizActivity.this, "Please select ans answer", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        showNextQuestion();
                    }
                }

            });
    }

    private void showNextQuestion()
    {
        rb1.setTextColor(textcolorDefaultRb);
        rb2.setTextColor(textcolorDefaultRb);
        rb3.setTextColor(textcolorDefaultRb);
        rb4.setTextColor(textcolorDefaultRb);

        rbGroup.clearCheck();
        if(questionCounter < totalquestioncount)
        {
            currentQuestion= questions.get(questionCounter);
            Log.e("currentquestion=",currentQuestion.getQuestion().toString());

            textViewQuestion.setText(currentQuestion.getQuestion());

            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            questionCounter++;
            textViewQuestionCount.setText("Question: "+questionCounter+"/"+totalquestioncount);
            answered=false;
            btnconfirm.setText("Confirm");
            btnskip.setVisibility(View.VISIBLE);
            timeLeftInMillis=COUNTDOWN_IN_MILLIS;
            startCountDown();

        }
        else
            {
              finishQuiz();
        }
    }


    private void finishQuiz()
    {
        //countDownTimer.cancel();
        Log.e("Data=>","uid="+MainActivity.uid+"\nMode="+mode+"\nCategory="+category+"\nSkip="+skip+"\nscore="+score);
        new Insert_history().execute();

    }

    private void startCountDown()
    {
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000)
        {
            @Override
            public void onTick(long milisec)
            {
                timeLeftInMillis=milisec;
                updateCountDownText();
            }

            @Override
            public void onFinish()
            {
                timeLeftInMillis = 0;
                updateCountDownText();
                //===checkAnswer();
            }
        }.start();
    }

    private  void updateCountDownText()
    {

        int minutes=(int)(timeLeftInMillis/1000)/60;
        int second=(int)(timeLeftInMillis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,second);

        textViewCountdown.setText(timeFormatted);

        if(timeLeftInMillis < 300000)
        {
            textViewCountdown.setTextColor(Color.RED);
        }
        else
        {
            textViewCountdown.setTextColor(getResources().getColor(R.color.green));
        }

    }

    private  void checkAnswer()
    {

        answered=true;
        countDownTimer.cancel();
        RadioButton rbselected=findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr=rbGroup.indexOfChild(rbselected)+1;
        if(answerNr==currentQuestion.getAnswerNr())
        {
            score++;
            Log.e("score=",score+"");
        }
        showSolution();

    }

    private void showSolution()
    {
        
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);
        switch(currentQuestion.getAnswerNr())
        {
            case 1:
                rb1.setTextColor(Color.GREEN);
                Toast.makeText(this, "Answer 1 is correct", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                Toast.makeText(this, "Answer 2 is correct", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                Toast.makeText(this, "Answer 3 is correct", Toast.LENGTH_SHORT).show();
                break;

            case 4:
                rb4.setTextColor(Color.GREEN);
                Toast.makeText(this, "Answer 4  is correct", Toast.LENGTH_SHORT).show();
                break;
        }

        if(questionCounter<totalquestioncount)
        {
            btnconfirm.setText("Next");
            btnskip.setVisibility(View.GONE);
        }
        else
        {

            btnconfirm.setText("Finish");
            btnskip.setVisibility(View.GONE);

        }
    }

    class fetch_question extends AsyncTask<String,String,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog=new ProgressDialog(QuizActivity.this);
            pdialog.setMessage("Please wait...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            questions =new ArrayList<>();
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("category",category));
            params.add(new BasicNameValuePair("mode",mode));

            try
            {

                JSONObject obj=json.makeHttpRequest(Util.quizUrl,"GET",params);
                array=obj.getJSONArray(QuestionsTags.Tag_array);

                for(int i=0;i<array.length();i++)
                {
                    JSONObject o = array.getJSONObject(i);
                    question=o.getString(QuestionsTags.Tag_question);
                    option1=o.getString(QuestionsTags.Tag_optn1);
                    option2=o.getString(QuestionsTags.Tag_optn2);
                    option3=o.getString(QuestionsTags.Tag_optn3);
                    option4=o.getString(QuestionsTags.Tag_optn4);
                    ans=Integer.parseInt(o.getString(QuestionsTags.Tag_and));
                    questionCat=o.getString(QuestionsTags.Tag_category);
                    question_mode=o.getString(QuestionsTags.Tag_mode);
                    questionsModel1.add(new QuestionsModel(question,option1,option2,option3,option4,ans,question_mode,questionCat));
                }

            }
            catch (Exception e)
            {
                Log.e("=",e+"");
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pdialog.dismiss();
            if(questionsModel1.isEmpty())
            {
                startActivity(new Intent(QuizActivity.this, MainRoomActivity.class));
                Toast.makeText(QuizActivity.this, "this type of category quiz not available in system", Toast.LENGTH_SHORT).show();
            }
            else
            {
                questions= questionsModel1;
                Collections.shuffle(questions);
                totalquestioncount= questions.size();
                showNextQuestion();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if(backPresedTime + 2000 > System.currentTimeMillis())
        {
            Toast.makeText(this, "Finish Quiz", Toast.LENGTH_SHORT).show();
        }

        backPresedTime = System.currentTimeMillis();

    }

    class Insert_history extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog=new ProgressDialog(QuizActivity.this);
            pdialog.setMessage("please wait...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(true);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair("uid",uid));
            params.add(new BasicNameValuePair("mode",mode));
            params.add(new BasicNameValuePair("category",category));
            params.add(new BasicNameValuePair("skip",skip+""));
            params.add(new BasicNameValuePair("score",score+""));

            try {

                JSONObject obj=jsonp.makeHttpRequest(Util.historyInsert_Url,"POST",params);
                int ans=obj.getInt("success");
                if(ans==1)
                {
                    cnt=1;
                }
                else
                {
                    cnt=0;
                }
            }
            catch (Exception e)
            {
                Log.e("Exception=++",e+"");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdialog.dismiss();
            if(cnt==1)
            {


                Toast.makeText(QuizActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuizActivity.this, HistoryActivity.class));

            }
            else
            {

                Toast.makeText(QuizActivity.this, "Sorry Try Again", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }
}