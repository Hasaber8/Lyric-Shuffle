package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create sample dates
        Calendar calendar = Calendar.getInstance();

        calendar.set(2024, Calendar.JANUARY, 15, 10, 30);
        Date date1 = calendar.getTime();

        calendar.set(2024, Calendar.FEBRUARY, 20, 15, 45);
        Date date2 = calendar.getTime();

        calendar.set(2024, Calendar.MARCH, 5, 9, 15);
        Date date3 = calendar.getTime();

        // Populate chat list with dates and fromCount
        List<Chat> chatList = new ArrayList<>();
        chatList.add(new Chat(10, date1));
        chatList.add(new Chat(5, date2));
        chatList.add(new Chat(12, date3));

        // Set the adapter
        ChatRecycler adapter = new ChatRecycler(chatList);
        chatRecyclerView.setAdapter(adapter);
    }
}
