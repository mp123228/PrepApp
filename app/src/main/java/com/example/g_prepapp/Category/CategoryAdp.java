package com.example.g_prepapp.Category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_prepapp.Quiz.QuizActivity;
import com.example.g_prepapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdp extends RecyclerView.Adapter<CategoryAdp.ViewholderRecy>{


    ArrayList<CategoryModel> arrayList=new ArrayList<>();
    Context context;

    public CategoryAdp(ArrayList<CategoryModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewholderRecy onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_category,parent,false);
        return new ViewholderRecy(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdp.ViewholderRecy holder, int position) {

        holder.txid.setText(arrayList.get(position).getCatId()+"");
        holder.txcat.setText(arrayList.get(position).getCatName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                   String catname=arrayList.get(position).getCatName();
                   String s= MainRoomActivity.sp.getSelectedItem().toString();
                  // Toast.makeText(context, "Mode="+s+"\nCategory="+catname, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(context, QuizActivity.class);
                intent.putExtra("mode",s);
                intent.putExtra("category",catname);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewholderRecy extends RecyclerView.ViewHolder
   {

       TextView txcat,txid;
       LinearLayout linearLayout;

       public ViewholderRecy(@NonNull @NotNull View itemView) {
           super(itemView);
           txcat=itemView.findViewById(R.id.txcat);
           txid=itemView.findViewById(R.id.txid);
           linearLayout=itemView.findViewById(R.id.llinear);
       }
   }
}
