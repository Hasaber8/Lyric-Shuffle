package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

import edu.northeastern.group_10_lyricshuffle.repository.SongRepository;
import edu.northeastern.group_10_lyricshuffle.model.Song;

public class DifficultySelectionActivity extends AppCompatActivity {

    private SongRepository songRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        songRepository = new SongRepository();

        MaterialCardView easyCard = findViewById(R.id.easyCard);
        MaterialCardView mediumCard = findViewById(R.id.mediumCard);
        MaterialCardView hardCard = findViewById(R.id.hardCard);

        easyCard.setOnClickListener(v -> selectDifficulty("easy"));
        mediumCard.setOnClickListener(v -> selectDifficulty("medium"));
        hardCard.setOnClickListener(v -> selectDifficulty("hard"));
    }

    private void selectDifficulty(String difficulty) {
        // Run song fetching on a background thread
        new Thread(() -> {
            // Fetch song based on difficulty
            Song song = songRepository.getRandomSongByDifficulty(difficulty);

            // Update UI on the main thread after fetching the song
            runOnUiThread(() -> {
                if (song != null) {
                    // Get the songId and pass it to the next activity
                    String songId = song.getSongId().toString(); // Convert UUID to String
                    Intent intent = new Intent(DifficultySelectionActivity.this, GameActivity.class);
                    intent.putExtra("songId", songId); // Pass the songId to GameActivity
                    startActivity(intent);
                } else {
                    // In case no song was found for this difficulty
                    Toast.makeText(this, "No song found for the selected difficulty", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
