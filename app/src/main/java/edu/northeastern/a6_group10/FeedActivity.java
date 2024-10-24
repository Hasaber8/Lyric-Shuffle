package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
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
}
