package com.example.assignment1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class requestadap extends RecyclerView.Adapter<requestadap.ItemViewHolder> {
    private List<request> itemList;

    private Context context;


    public requestadap(List<request> itemList) {
        this.itemList = itemList;

    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent, false);
        return new requestadap.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        request item = itemList.get(position);


        holder.itemid.setText(item.getItemId());
        holder.requestid.setText(item.getRequesterId());




        holder.row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,itemdet.class);
                intent.putExtra("itemid",itemList.get(position).getItemId());
                intent.putExtra("requestid",itemList.get(position).getRequesterId());
                intent.putExtra("ownerid",itemList.get(position).getOwnerId());


                context.startActivity(intent);

//                Intent intent=new Intent();
//                Uri url= Uri.parse("https://www.google.com");
//                intent.setData(url);
//                intent.setAction(Intent.ACTION_VIEW);
//                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemid;
        TextView requestid;

        Button btn1;
        Button btn2;
        LinearLayout row2;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemid = itemView.findViewById(R.id.itemid);
            requestid = itemView.findViewById(R.id.requestid);
            btn1= itemView.findViewById(R.id.btnaccept);
            btn2= itemView.findViewById(R.id.btnreject);

            row2=itemView.findViewById(R.id.row2);


            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Accepted", Toast.LENGTH_SHORT).show();

                }
            });

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Rejected", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

}


