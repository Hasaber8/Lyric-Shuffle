package edu.northeastern.group_10_lyricshuffle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

import edu.northeastern.group_10_lyricshuffle.R;
import edu.northeastern.group_10_lyricshuffle.model.RecentPlay;

public class RecentPlaysAdapter extends RecyclerView.Adapter<RecentPlaysAdapter.ViewHolder> {
    private List<RecentPlay> recentPlays;

    public RecentPlaysAdapter(List<RecentPlay> recentPlays) {
        this.recentPlays = recentPlays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_play, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentPlay play = recentPlays.get(position);
        String title = play.getSongName() + " by " + play.getArtist();
        holder.titleView.setText(title);
        holder.scoreView.setText(String.format(Locale.getDefault(), "%,.0f", play.getScore()));

        // Format time ago
        Duration duration = Duration.between(play.getDatePlayed(), Instant.now());
        String timeAgo = formatTimeAgo(duration);
        holder.timeAgoView.setText(timeAgo);
    }

    @Override
    public int getItemCount() {
        return recentPlays.size();
    }

    public void updateData(List<RecentPlay> newPlays) {
        this.recentPlays = newPlays;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView scoreView;
        TextView timeAgoView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.songTitle);
            scoreView = view.findViewById(R.id.score);
            timeAgoView = view.findViewById(R.id.timeAgo);
        }
    }

    private String formatTimeAgo(Duration duration) {
        if (duration.toMinutes() < 60) {
            return duration.toMinutes() + "m ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + "h ago";
        } else {
            return duration.toDays() + "d ago";
        }
    }
}
