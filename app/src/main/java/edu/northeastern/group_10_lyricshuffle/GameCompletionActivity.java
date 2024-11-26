package edu.northeastern.group_10_lyricshuffle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameCompletionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_completion);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        String songTitle = intent.getStringExtra("songTitle");
        String artistName = intent.getStringExtra("artistName");
        int totalScore = intent.getIntExtra("totalScore", 0);
        int correctLines = intent.getIntExtra("correctLines", 0);
        int timeBonus = intent.getIntExtra("timeBonus", 0);
        String totalTime = intent.getStringExtra("totalTime");

        // Set data to views
        ((TextView) findViewById(R.id.songTitle)).setText(songTitle);
        ((TextView) findViewById(R.id.artistName)).setText(artistName);
        ((TextView) findViewById(R.id.totalScoreValue)).setText(String.valueOf(totalScore));
        ((TextView) findViewById(R.id.correctLinesValue)).setText(String.valueOf(correctLines));
        ((TextView) findViewById(R.id.timeBonusValue)).setText(String.valueOf(timeBonus));
        ((TextView) findViewById(R.id.totalTimeValue)).setText(totalTime);

        findViewById(R.id.leaderboardCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leaderboardIntent = new Intent(GameCompletionActivity.this, LeaderboardActivity.class);
                startActivity(leaderboardIntent);
            }
        });

        findViewById(R.id.playAgainCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(GameCompletionActivity.this, DifficultySelectionActivity.class);
                // Clear all activities up to MainActivity (home)
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}