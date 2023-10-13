package com.example.assignment1;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;


public class recycleadapter extends RecyclerView.Adapter<recycleadapter.ItemViewHolder> {

    private List<itemcard> itemList;

    Context context;


    public recycleadapter(List<itemcard> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        itemcard item = itemList.get(position);

        holder.itemImage.setImageBitmap(item.getImg());
        holder.itemName.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.views.setText(String.valueOf(item.getViews()));
        holder.date.setText(item.getDate());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,itemdet.class);
                intent.putExtra("name",itemList.get(position).getName());
                intent.putExtra("price",itemList.get(position).getPrice());
                intent.putExtra("views",itemList.get(position).getViews());
                intent.putExtra("date",itemList.get(position).getDate());
                intent.putExtra("img",itemList.get(position).getImg());
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
        ImageView itemImage;
        TextView itemName;
        TextView price;
        TextView views;
        TextView date;
       RelativeLayout row;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            views = itemView.findViewById(R.id.views);
            date = itemView.findViewById(R.id.date);
            row=itemView.findViewById(R.id.row);
        }
    }


}
