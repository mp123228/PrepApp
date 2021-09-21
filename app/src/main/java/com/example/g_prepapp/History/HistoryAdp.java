package com.example.g_prepapp.History;

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
import java.util.HashMap;

public class HistoryAdp extends RecyclerView.Adapter<HistoryAdp.ViewholderHistory>{


    ArrayList<HistoryModel> hashMaps=new ArrayList<>();
    Context context;

    public HistoryAdp(ArrayList<HistoryModel> hashMaps, Context context) {
        this.hashMaps = hashMaps;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewholderHistory onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_history,parent,false);
        return new ViewholderHistory(view);
    }




    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryAdp.ViewholderHistory holder, int position) {
        holder.txuid.setText("Username="+hashMaps.get(position).getUid());
        holder.txmode.setText("Mode="+hashMaps.get(position).getMode());
        holder.txcategory.setText("Category="+hashMaps.get(position).getCategory());
        holder.txscore.setText("Score="+hashMaps.get(position).getScore());
        holder.txskip.setText("Skip="+hashMaps.get(position).getSkip());
        holder.txdate.setText("Date="+hashMaps.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return hashMaps.size();
    }

    class ViewholderHistory extends RecyclerView.ViewHolder
    {

        TextView txuid,txmode,txcategory,txskip,txscore,txdate;
        public ViewholderHistory(@NonNull @NotNull View itemView) {
            super(itemView);
            txuid=itemView.findViewById(R.id.tuid);
            txcategory=itemView.findViewById(R.id.category);
            txmode=itemView.findViewById(R.id.mode);
            txscore=itemView.findViewById(R.id.score);
            txskip=itemView.findViewById(R.id.skip);
            txdate=itemView.findViewById(R.id.date);
        }
    }
}
