package com.example.assignment1;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class imageslideradap extends RecyclerView.Adapter<imageslideradap.ImageSlideViewHolder> {

    private List<Integer> imageList; // List of image resource IDs

    public imageslideradap(List<Integer> img) {
    }

    public void imageslideradapr(List<Integer> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageSlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageslide, parent, false);
        return new ImageSlideViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlideViewHolder holder, int position) {
        int imageResource = imageList.get(position);
        holder.imageView.setImageResource(imageResource);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageSlideViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageSlideViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
