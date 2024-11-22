package edu.northeastern.group_10_lyricshuffle;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.adapter.ArrangedLyricsAdapter;
import edu.northeastern.group_10_lyricshuffle.adapter.AvailableLyricsAdapter;
import edu.northeastern.group_10_lyricshuffle.model.LyricLine;
import edu.northeastern.group_10_lyricshuffle.repository.LyricRepository;
import edu.northeastern.group_10_lyricshuffle.repository.UserRepository;
import edu.northeastern.group_10_lyricshuffle.service.AuthService;
import edu.northeastern.group_10_lyricshuffle.util.UserSession;

public class GameActivity extends AppCompatActivity implements
        ArrangedLyricsAdapter.OnLyricClickListener,
        AvailableLyricsAdapter.OnLyricClickListener {

    private RecyclerView arrangedLyricsRecyclerView;
    private RecyclerView availableLyricsRecyclerView;
    private ArrangedLyricsAdapter arrangedAdapter;
    private AvailableLyricsAdapter availableAdapter;
    private MaterialButton submitButton;
    private TextView timerText;
    private TextView scoreText;
    private List<LyricLine> originalLyrics;
    private int currentScore = 0;
    private CountDownTimer timer;
    private static final int GAME_DURATION = 120000; // 2 minutes in milliseconds
    private static final int POINTS_PER_CORRECT_LINE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        originalLyrics = new ArrayList<>();
        String songIdString = getIntent().getStringExtra("songId");
        UUID songId = UUID.fromString(songIdString);
        initializeViews();
        setupRecyclerViews();
        setupTimer();
        initializeOriginalLyrics(songId);
        loadLyrics();
        setupButtons();
    }

    private void initializeViews() {
        arrangedLyricsRecyclerView = findViewById(R.id.arrangedLyricsRecyclerView);
        availableLyricsRecyclerView = findViewById(R.id.availableLyricsRecyclerView);
        submitButton = findViewById(R.id.submitButton);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);

        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton refreshButton = findViewById(R.id.refreshButton);

        backButton.setOnClickListener(v -> onBackPressed());
        refreshButton.setOnClickListener(v -> resetLyrics());

        // Initialize score display
        scoreText.setText("0");
    }

    private void setupRecyclerViews() {
        // Initialize adapters
        arrangedAdapter = new ArrangedLyricsAdapter(this);
        availableAdapter = new AvailableLyricsAdapter(this);

        // Setup layout managers
        arrangedLyricsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        availableLyricsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set adapters
        arrangedLyricsRecyclerView.setAdapter(arrangedAdapter);
        availableLyricsRecyclerView.setAdapter(availableAdapter);
    }

    private void setupTimer() {
        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(GAME_DURATION, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerText.setText(String.format("%d:%02d", minutes, seconds));
            }

            public void onFinish() {
                timerText.setText("0:00");
                checkAnswer(true);
            }
        }.start();
    }

    // wip - dummy lyrics
    // actual impl will fetch it from db.
//    private void initializeOriginalLyrics() {
//        originalLyrics = new ArrayList<>();
//        originalLyrics.add(new LyricLine("But she wears short skirts, I wear t-shirts", POINTS_PER_CORRECT_LINE, 0));
//        originalLyrics.add(new LyricLine("She's cheer captain and I'm on the bleachers", POINTS_PER_CORRECT_LINE, 1));
//        originalLyrics.add(new LyricLine("Dreaming about the day when you wake up and find", POINTS_PER_CORRECT_LINE, 2));
//        originalLyrics.add(new LyricLine("That what you're looking for has been here the whole time", POINTS_PER_CORRECT_LINE, 3));
//    }

    private void initializeOriginalLyrics(UUID songId) {
        // Run lyrics fetching on a background thread
        Log.d("GameActivity", "Fetching lyrics for songId: " + songId);
        new Thread(() -> {
            LyricRepository lyricRepository = new LyricRepository();
            List<LyricLine> lyrics = lyricRepository.getLyricsBySongId(songId);
            Log.d("GameActivity", "Fetched lyrics: " + lyrics + " for songId: " + songId);
            // Update UI on the main thread after fetching the lyrics
            runOnUiThread(() -> {
                if (lyrics.isEmpty()) {
                    // Fallback if no lyrics are found
                    lyrics.add(new LyricLine("Dummy lyric 1", POINTS_PER_CORRECT_LINE, 0));
                    lyrics.add(new LyricLine("Dummy lyric 2", POINTS_PER_CORRECT_LINE, 1));
                }

                // Set the lyrics to originalLyrics and update the UI
                originalLyrics = lyrics;

                // Call any methods to update UI based on the lyrics
                loadLyrics();
            });
        }).start();
    }


    private void loadLyrics() {
        // Create a copy of original lyrics and shuffle them
        Log.d("GameActivity", "Original lyrics: " + originalLyrics);
        List<LyricLine> shuffledLyrics = new ArrayList<>(originalLyrics);
        Collections.shuffle(shuffledLyrics);

        // Set the shuffled lyrics to available adapter
        availableAdapter.setLyrics(shuffledLyrics);

        // Ensure arranged adapter is empty
        arrangedAdapter = new ArrangedLyricsAdapter(this);
        arrangedLyricsRecyclerView.setAdapter(arrangedAdapter);

        updateSubmitButton();
    }

    private void resetLyrics() {
        // Move all lyrics back to available section in their original shuffled order
        availableAdapter.resetToOriginalOrder();

        // Clear arranged lyrics
        arrangedAdapter = new ArrangedLyricsAdapter(this);
        arrangedLyricsRecyclerView.setAdapter(arrangedAdapter);

        updateSubmitButton();
    }

    private void setupButtons() {
        submitButton.setOnClickListener(v -> checkAnswer(false));
        submitButton.setEnabled(false);
    }

    @Override
    public void onLyricSelected(int position) {
        LyricLine lyric = availableAdapter.getLyric(position);
        if (lyric != null) {
            availableAdapter.removeLyric(position);
            arrangedAdapter.addLyric(lyric);
            updateSubmitButton();

            // Scroll to show the newly added lyric
            arrangedLyricsRecyclerView.post(() ->
                    arrangedLyricsRecyclerView.scrollToPosition(arrangedAdapter.getItemCount() - 1));
        }
    }

    @Override
    public void onLyricRemoved(int position) {
        LyricLine lyric = arrangedAdapter.getLyric(position);
        if (lyric != null) {
            arrangedAdapter.removeLyric(position);
            availableAdapter.addLyricBack(lyric);
            updateSubmitButton();
        }
    }

    private void updateSubmitButton() {
        submitButton.setEnabled(availableAdapter.getItemCount() == 0);
    }

    // wip - dummy check answer
//    private void checkAnswer(boolean timeOut) {
//        if (timer != null) {
//            timer.cancel();
//        }
//
//        List<LyricLine> arrangedLyrics = arrangedAdapter.getLyrics();
//        int totalPoints = 0;
//        boolean allCorrect = true;
//
//        // Check each lyric's position
//        for (int i = 0; i < arrangedLyrics.size(); i++) {
//            if (arrangedLyrics.get(i).getCorrectPosition() == i) {
//                totalPoints += POINTS_PER_CORRECT_LINE;
//            } else {
//                allCorrect = false;
//            }
//        }
//
//        // Update score
//        currentScore += totalPoints;
//        scoreText.setText(String.valueOf(currentScore));
//
//        showResultDialog(allCorrect, totalPoints, timeOut);
//    }

    private void checkAnswer(boolean timeOut) {
        if (timer != null) {
            timer.cancel();
        }

        List<LyricLine> arrangedLyrics = arrangedAdapter.getLyrics();
        int totalPoints = 0;
        boolean allCorrect = true;

        // Check each lyric's position
        for (int i = 0; i < arrangedLyrics.size(); i++) {
            if (arrangedLyrics.get(i).getCorrectPosition() == i) {
                totalPoints += POINTS_PER_CORRECT_LINE;
            } else {
                allCorrect = false;
            }
        }

        // Update score
        currentScore += totalPoints;
        scoreText.setText(String.valueOf(currentScore));

        // Save score to DB
        UserRepository userRepository = new UserRepository();
        UserSession userSession = UserSession.getInstance(this);
        UUID userId = userSession.getUserId();

        boolean scoreSaved = userRepository.saveScore(userId, totalPoints);

        if (!scoreSaved) {
            Log.e("GameActivity", "Failed to save score to database");
        }

        showResultDialog(allCorrect, totalPoints, timeOut);
    }


    // wip - dummy result dialog
    private void showResultDialog(boolean allCorrect, int points, boolean timeOut) {
        String title;
        String message;

        if (timeOut) {
            title = "Time's Up!";
            message = String.format("You earned %d points", points);
        } else if (allCorrect) {
            title = "Perfect!";
            message = String.format("Congratulations! You earned %d points", points);
        } else {
            title = "Nice Try!";
            message = String.format("You earned %d points. Keep practicing!", points);
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Try Again", (dialog, which) -> {
                    resetLyrics();
                    setupTimer();
                })
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}