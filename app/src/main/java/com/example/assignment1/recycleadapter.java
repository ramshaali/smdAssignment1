package com.example.assignment1;



import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;


public class recycleadapter extends RecyclerView.Adapter<recycleadapter.ItemViewHolder> {

    private List<itemcard> itemList;

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

        holder.itemImage.setImageResource(item.getImg());
        holder.itemName.setText(item.getName());
        holder.price.setText(item.getPrice());
        holder.views.setText(String.valueOf(item.getViews()));
        holder.date.setText(item.getDate());
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

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.price);
            views = itemView.findViewById(R.id.views);
            date = itemView.findViewById(R.id.date);
        }
    }


}
