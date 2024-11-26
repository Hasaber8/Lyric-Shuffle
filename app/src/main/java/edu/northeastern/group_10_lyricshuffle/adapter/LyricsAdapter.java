package edu.northeastern.group_10_lyricshuffle.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LyricsAdapter extends RecyclerView.Adapter<LyricsAdapter.LyricViewHolder> {

    private final List<String> lyrics;
    private final boolean isSelectable; // True for Available Lyrics
    private String highlightLyric = null; // Highlight this lyric in the list
    private OnLyricClickListener onLyricClickListener;

    public LyricsAdapter(List<String> lyrics, boolean isSelectable) {
        this.lyrics = lyrics;
        this.isSelectable = isSelectable;
    }

    @NonNull
    @Override
    public LyricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new LyricViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LyricViewHolder holder, int position) {
        String lyric = lyrics.get(position);
        holder.textView.setText(lyric);

        if (highlightLyric != null && lyric.equals(highlightLyric)) {
            holder.textView.setBackgroundColor(Color.parseColor("#D6EFFF")); // Highlight
            holder.textView.setEnabled(true);
        } else if (isSelectable) {
            holder.textView.setBackgroundColor(Color.LTGRAY); // Dim
            holder.textView.setEnabled(false);
        }

        holder.textView.setOnClickListener(v -> {
            if (onLyricClickListener != null && holder.textView.isEnabled()) {
                onLyricClickListener.onLyricClick(lyric);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    public void setHighlight(String lyric) {
        this.highlightLyric = lyric;
        notifyDataSetChanged();
    }

    public void setOnLyricClickListener(OnLyricClickListener listener) {
        this.onLyricClickListener = listener;
    }

    static class LyricViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        LyricViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnLyricClickListener {
        void onLyricClick(String lyric);
    }
}
