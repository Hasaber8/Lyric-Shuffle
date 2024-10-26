package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // get the current username from the shared preferences
        currentUsername = getSharedPreferences(Constants.LOCAL_DATASTORE_STICKERS, MODE_PRIVATE)
                .getString(Constants.LOCAL_DATASTORE_USERNAME_KEY, "");

        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // get the history from the database
        List<Chat> chatList = new ArrayList<>();
        String historyPath = "messages/" + currentUsername + "/history/";
        DatabaseReference chatHistoryRef = db.getReference(historyPath);

        chatHistoryRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot chatSnapshot : task.getResult().getChildren()) {
                    Chat chat = chatSnapshot.getValue(Chat.class);
                    Log.d("ChatActivity", "Chat: " + chat);
                    if (chat == null) {
                        Log.e("ChatActivity", "Chat is null");
                        continue;
                    }
                    chatList.add(chat);
                }
            } else {
                // Handle error
                Log.e("ChatActivity", "Error getting chat history", task.getException());
            }
        });

        // Set the adapter
        ChatRecycler adapter = new ChatRecycler(chatList);
        chatRecyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottomNavigationChat);
        bottomNavigationMenuView.setSelectedItemId(R.id.nav_chats); // Set default selected item

        bottomNavigationMenuView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_chats) {
                // We're already on ChatActivity, so no action is needed
                return true;
            } else if (item.getItemId() == R.id.nav_stats) {
                Intent intent = new Intent(ChatActivity.this, FeedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return false; //so that this does not remain selected when the activity comes into focus
            }
            return false;
        });
    }

}
