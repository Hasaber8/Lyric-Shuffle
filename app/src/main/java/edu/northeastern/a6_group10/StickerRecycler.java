package edu.northeastern.a6_group10;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StickerRecycler extends RecyclerView.Adapter<StickerRecycler.StickerViewHolder> {

    private List<Sticker> stickerList;

    public StickerRecycler(List<Sticker> stickerList) {
        this.stickerList = stickerList;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_item, parent, false);
        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickerList.get(position);
        holder.stickerName.setText(sticker.getName());
        holder.stickerCount.setText("Sent: " + sticker.getCount());

        String imageUrl = sticker.getImageUrl();
        Log.d("Sticker URL", "Loading URL: " + imageUrl);

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.stickerIcon);
    }


    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateStickerList(List<Sticker> newStickerList) {
        Log.d("StickerRecycler", "Updating sticker list");
        this.stickerList = newStickerList;
        notifyDataSetChanged();
    }

    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerIcon;
        TextView stickerName, stickerCount;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerIcon = itemView.findViewById(R.id.stickerIcon);
            stickerName = itemView.findViewById(R.id.stickerName);
            stickerCount = itemView.findViewById(R.id.sentNumberValue);
        }
    }
}

