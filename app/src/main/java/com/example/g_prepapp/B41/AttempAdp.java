package com.example.g_prepapp.B41;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_prepapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AttempAdp extends RecyclerView.Adapter<AttempAdp.AttempViewholder> {

    ArrayList<AuthModel> authModels = new ArrayList<>();
    Context context;

    int count = 1;

    public AttempAdp(ArrayList<AuthModel> authModels, Context context) {
        this.authModels = authModels;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AttempViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.row_attemp_layout, parent, false);
        return new AttempViewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AttempAdp.AttempViewholder holder, int position) {


        holder.email.setText(count+": Username: " + authModels.get(position).email);
        holder.daynm.setText("Day: " + authModels.get(position).dayname);
        holder.daymon.setText("Month: " + authModels.get(position).monthname);
        holder.date.setText("Date: " + authModels.get(position).date);
        holder.time.setText("Time: " + authModels.get(position).time);
        holder.log.setText("Log: " + authModels.get(position).loginout);
        count++;

    }

    @Override
    public int getItemCount() {
        //only 7 list show
        int k=0;
        k= authModels.size();
        if(k>7)
        {
            return 7;
        }
        else
            {
            return authModels.size();
            }

    }

    class AttempViewholder extends RecyclerView.ViewHolder
    {
        TextView email,daynm,daymon,date,time,log;

        public AttempViewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            email=itemView.findViewById(R.id.adpemail);
            daynm=itemView.findViewById(R.id.adpdayname);
            daymon=itemView.findViewById(R.id.adpmonthname);
            date=itemView.findViewById(R.id.adpdate);
            log=itemView.findViewById(R.id.adplog);
            time=itemView.findViewById(R.id.adptime);

        }
    }
}
