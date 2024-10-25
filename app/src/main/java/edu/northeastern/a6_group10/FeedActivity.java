package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView stickerRecyclerView = findViewById(R.id.stickerRecyclerView);
        stickerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Temp data. Fetch stickers and add sticker image for each
        List<Sticker> stickerList = new ArrayList<>();
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));
        stickerList.add(new Sticker( "Funny Cat", 10));
        stickerList.add(new Sticker("Cute Dog", 5));
        stickerList.add(new Sticker("Cool Emoji", 12));

        // Set the adapter
        StickerRecycler adapter = new StickerRecycler(stickerList);
        stickerRecyclerView.setAdapter(adapter);
    }

    public void onSelectSticker(View view){
        int stickerId = view.getId();
        Intent intent = new Intent(FeedActivity.this, UserProfileListActivity.class);
        intent.putExtra("selectedStickerId", stickerId);
        startActivity(intent); // Start the new activity
    }
    public void onClickChatTab(View view){
        Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
        startActivity(intent); // Start the new activity
    }

    public void onClickStatTab(View view){
        Intent intent = new Intent(FeedActivity.this, FeedActivity.class);
        startActivity(intent); // Start the new activity
    }

}
