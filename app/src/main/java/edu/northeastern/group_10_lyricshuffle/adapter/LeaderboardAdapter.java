package edu.northeastern.group_10_lyricshuffle.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.List;
import edu.northeastern.group_10_lyricshuffle.R;
import edu.northeastern.group_10_lyricshuffle.model.LeaderboardEntry;
import edu.northeastern.group_10_lyricshuffle.util.IdenticonGenerator;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {
    private final List<LeaderboardEntry> entries;

    public LeaderboardAdapter(List<LeaderboardEntry> entries) {
        this.entries = entries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardEntry entry = entries.get(position);

        // Set rank
        holder.rankText.setText(String.valueOf(entry.getRank()));

        // Generate and set identicon
        Bitmap identicon = IdenticonGenerator.generate(entry.getUserName());
        holder.profileImage.setImageBitmap(identicon);

        // Set username
        holder.userName.setText(entry.getUserName());

        // Set score
        holder.scoreText.setText(String.format("%,d pts", entry.getScore()));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rankText;
        ShapeableImageView profileImage;
        TextView userName;
        TextView scoreText;

        ViewHolder(View itemView) {
            super(itemView);
            rankText = itemView.findViewById(R.id.rankText);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userName);
            scoreText = itemView.findViewById(R.id.scoreText);
        }
    }

    public void updateData(List<LeaderboardEntry> newEntries) {
        this.entries.clear();
        this.entries.addAll(newEntries);
        notifyDataSetChanged();
    }

}
