package com.example.g_prepapp.Learn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_prepapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LearnCategoryAdp extends RecyclerView.Adapter<LearnCategoryAdp.learnViewholder>{

    Context context;
    ArrayList<LearnCateogoryModel> learnCateogoryModel =new ArrayList<>();

    public LearnCategoryAdp(Context context, ArrayList<LearnCateogoryModel> learnCateogoryModel)
    {

        this.context = context;
        this.learnCateogoryModel = learnCateogoryModel;

    }

    @NonNull
    @NotNull
    @Override
    public learnViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_learn,parent,false);
        return new learnViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LearnCategoryAdp.learnViewholder holder, int position) {

            holder.txcatlearn.setText(learnCateogoryModel.get(position).getCategory());

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context,LearningActivity.class);
                    intent.putExtra("lcategory",holder.txcatlearn.getText().toString());
                    context.startActivity(intent);

                }
            });

    }

    @Override
    public int getItemCount() {
        return learnCateogoryModel.size();
    }

    class learnViewholder extends RecyclerView.ViewHolder
    {

        TextView txcatlearn;
        LinearLayout linearLayout;
        public learnViewholder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            txcatlearn=itemView.findViewById(R.id.txlearn);
            linearLayout=itemView.findViewById(R.id.idlearn);

        }
    }
}
