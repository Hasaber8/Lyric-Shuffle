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

public class ArrangedLyricsAdapter extends RecyclerView.Adapter<ArrangedLyricsAdapter.ViewHolder> {
    private final List<LyricLine> lyrics;
    private final OnLyricClickListener listener;

    public ArrangedLyricsAdapter(OnLyricClickListener listener) {
        this.lyrics = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_arranged_lyric, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LyricLine line = lyrics.get(position);
        holder.lyricText.setText(line.getText());

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                listener.onLyricRemoved(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    public void addLyric(LyricLine lyric) {
        lyrics.add(lyric);
        notifyItemInserted(lyrics.size() - 1);
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

    public LyricLine getLyric(int position) {
        if (position >= 0 && position < lyrics.size()) {
            return lyrics.get(position);
        }
        return null;
    }

    public List<LyricLine> getLyrics() {
        return new ArrayList<>(lyrics);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView lyricText;

        ViewHolder(View itemView) {
            super(itemView);
            lyricText = itemView.findViewById(R.id.lyricText);
        }
    }

    public interface OnLyricClickListener {
        void onLyricRemoved(int position);
    }
}