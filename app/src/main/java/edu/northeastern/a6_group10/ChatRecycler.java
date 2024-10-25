package edu.northeastern.a6_group10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatRecycler extends RecyclerView.Adapter<ChatRecycler.ChatViewHolder> {

    private List<Chat> chatList;

    public ChatRecycler(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.chatFromCount.setText("From: " +chat.getFromCount());
        holder.chatTimestamp.setText("Timestamp: " + chat.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatFromCount;
        TextView chatTimestamp;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatFromCount = itemView.findViewById(R.id.sentNumberValue);
            chatTimestamp = itemView.findViewById(R.id.sentNumberValue);
        }
    }
}

