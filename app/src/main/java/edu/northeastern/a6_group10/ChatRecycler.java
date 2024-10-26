package edu.northeastern.a6_group10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatRecycler extends RecyclerView.Adapter<ChatRecycler.ChatViewHolder> {

    private final List<Chat> chatList;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

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
        holder.chatSender.setText("From: " + chat.getSenderId());

        // Convert epoch time (ServerValue.TIMESTAMP) to formatted date string
        Date date = new Date(chat.getTimestamp());
        String formattedDate = dateFormat.format(date);
        holder.chatTimestamp.setText("Timestamp: " + formattedDate);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chatSender;
        TextView chatTimestamp;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatSender = itemView.findViewById(R.id.senderValue);
            chatTimestamp = itemView.findViewById(R.id.timestampValue);
        }
    }
}
