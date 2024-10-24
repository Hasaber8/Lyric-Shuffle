package edu.northeastern.a6_group10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        //holder.stickerLogo.setImageResource(sticker.getLogoResId());
        holder.stickerName.setText(sticker.getName());
        holder.stickerCount.setText("Sent: " + sticker.getCount());
    }

    @Override
    public int getItemCount() {
        return stickerList.size();
    }

    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        ImageView stickerLogo;
        TextView stickerName, stickerCount;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            //stickerLogo = itemView.findViewById(R.id.stickerLogo);
            stickerName = itemView.findViewById(R.id.stickerName);
            stickerCount = itemView.findViewById(R.id.stickerCount);
        }
    }
}

