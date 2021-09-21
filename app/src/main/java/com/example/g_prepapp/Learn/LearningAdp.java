package com.example.g_prepapp.Learn;

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

public class LearningAdp extends RecyclerView.Adapter<LearningAdp.LearningViewholder>
{
    Context context;
    ArrayList<LearningModel> learningModelArrayList=new ArrayList<>();
    int cnt=1;

    public LearningAdp(Context context, ArrayList<LearningModel> learningModelArrayList) {
        this.context = context;
        this.learningModelArrayList = learningModelArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public LearningViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_questionans_learning,parent,false);
        return new LearningViewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LearningAdp.LearningViewholder holder, int position) {

            holder.txquestion.setText(cnt+". "+learningModelArrayList.get(position).getLearnQuestion());
            holder.txans.setText("Ans="+learningModelArrayList.get(position).getLearnAns());

            cnt++;

    }

    @Override
    public int getItemCount() {

        return learningModelArrayList.size();

    }


    class LearningViewholder extends RecyclerView.ViewHolder
    {

        TextView txquestion,txans;
        public LearningViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            txquestion=itemView.findViewById(R.id.idqustion);
            txans=itemView.findViewById(R.id.idans);
        }
    }
}
