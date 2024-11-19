package edu.northeastern.group_10_lyricshuffle.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.group_10_lyricshuffle.R;
import edu.northeastern.group_10_lyricshuffle.model.LyricLine;

public class AvailableLyricsAdapter extends RecyclerView.Adapter<AvailableLyricsAdapter.ViewHolder> {
    private final List<LyricLine> lyrics;
    private final List<LyricLine> originalOrder;
    private final OnLyricClickListener listener;

    public AvailableLyricsAdapter(OnLyricClickListener listener) {
        this.lyrics = new ArrayList<>();
        this.originalOrder = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_available_lyric, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LyricLine line = lyrics.get(position);
        holder.lyricText.setText(line.getText());

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                listener.onLyricSelected(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    public void setLyrics(List<LyricLine> newLyrics) {
        lyrics.clear();
        originalOrder.clear();
        lyrics.addAll(newLyrics);
        originalOrder.addAll(newLyrics);
        notifyDataSetChanged();
    }

    public void removeLyric(int position) {
        if (position >= 0 && position < lyrics.size()) {
            lyrics.remove(position);
            notifyItemRemoved(position);
            if (lyrics.size() > position) {
                notifyItemRangeChanged(position, lyrics.size() - position);
            }
        }
    }

    public void addLyricBack(LyricLine lyric) {
        if (lyrics.isEmpty()) {
            // If the list is empty, simply add the lyric
            lyrics.add(lyric);
            notifyItemInserted(0);
            return;
        }

        // Find the position in the original order
        int originalPosition = -1;
        for (int i = 0; i < originalOrder.size(); i++) {
            if (originalOrder.get(i).getCorrectPosition() == lyric.getCorrectPosition()) {
                originalPosition = i;
                break;
            }
        }

        // Bug - it kept crashing when the available lyrics were empty
        // If we couldn't find the original position (shouldn't happen), add to end
        if (originalPosition == -1) {
            lyrics.add(lyric);
            notifyItemInserted(lyrics.size() - 1);
            return;
        }

        // Find where to insert in current list
        int insertPosition = lyrics.size(); // Default to end
        for (int i = 0; i < lyrics.size(); i++) {
            int currentOriginalPos = -1;
            for (int j = 0; j < originalOrder.size(); j++) {
                if (originalOrder.get(j).getCorrectPosition() == lyrics.get(i).getCorrectPosition()) {
                    currentOriginalPos = j;
                    break;
                }
            }
            if (currentOriginalPos > originalPosition) {
                insertPosition = i;
                break;
            }
        }

        lyrics.add(insertPosition, lyric);
        notifyItemInserted(insertPosition);
    }

    public LyricLine getLyric(int position) {
        if (position >= 0 && position < lyrics.size()) {
            return lyrics.get(position);
        }
        return null;
    }

    public void resetToOriginalOrder() {
        lyrics.clear();
        lyrics.addAll(originalOrder);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView lyricText;

        ViewHolder(View itemView) {
            super(itemView);
            lyricText = itemView.findViewById(R.id.lyricText);
        }
    }

    public interface OnLyricClickListener {
        void onLyricSelected(int position);
    }
}