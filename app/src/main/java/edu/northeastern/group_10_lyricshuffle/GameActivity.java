package edu.northeastern.group_10_lyricshuffle;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import edu.northeastern.group_10_lyricshuffle.adapter.ArrangedLyricsAdapter;
import edu.northeastern.group_10_lyricshuffle.adapter.AvailableLyricsAdapter;
import edu.northeastern.group_10_lyricshuffle.model.LyricLine;
import edu.northeastern.group_10_lyricshuffle.repository.LyricRepository;
import edu.northeastern.group_10_lyricshuffle.repository.PlaySessionRepository;
import edu.northeastern.group_10_lyricshuffle.util.UserSession;

public class GameActivity extends AppCompatActivity implements
        ArrangedLyricsAdapter.OnLyricClickListener,
        AvailableLyricsAdapter.OnLyricClickListener,
        SensorEventListener {

    private RecyclerView arrangedLyricsRecyclerView;
    private RecyclerView availableLyricsRecyclerView;
    private ArrangedLyricsAdapter arrangedAdapter;
    private AvailableLyricsAdapter availableAdapter;
    private MaterialButton submitButton;
    private TextView timerText;
    private TextView difficultyText;
    private List<LyricLine> originalLyrics;
    private int currentScore = 0;
    private int correctLines = 0;
    private String songName;
    private String artistName;
    private String difficulty;
    private String difficultyCapital;
    private CountDownTimer timer;
    private static final int GAME_DURATION = 60000;
    private static final int POINTS_PER_CORRECT_LINE = 100;
    private UUID songId;
    private int difficultyMultiplier;
    private MediaPlayer tickSoundPlayer;

    // Shake detection
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 8.0f;
    private static final int MIN_TIME_BETWEEN_SHAKES = 800; // in ms
    private long lastShakeTime;
    private float lastX = 0;
    private float lastY = 0;
    private float lastZ = 0;
    private boolean isFirstSensorReading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        originalLyrics = new ArrayList<>();

        String songIdString = getIntent().getStringExtra("songId");
        if (songIdString == null) {
            Log.e("GameActivity", "No songId provided in Intent");
            Toast.makeText(this, "Error: Invalid song data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            songId = UUID.fromString(songIdString);
        } catch (IllegalArgumentException e) {
            Log.e("GameActivity", "Invalid UUID format: " + songIdString, e);
            Toast.makeText(this, "Error: Invalid song data format", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        songName = getIntent().getStringExtra("songName");
        artistName = getIntent().getStringExtra("artistName");
        difficultyMultiplier = getIntent().getIntExtra("multiplier", 1);
        difficulty = getIntent().getStringExtra("difficulty");
        difficultyCapital = StringUtils.capitalize(difficulty);

        initializeViews();
        setupRecyclerViews();
        setupTimer();
        initializeOriginalLyrics(songId);
        loadLyrics();
        setupButtons();

        // Shake detection
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        // Show the shake instruction Snackbar
        Snackbar.make(findViewById(android.R.id.content),
                "Shake to reset the lyrics!",
                Snackbar.LENGTH_SHORT).show();
    }

    private void initializeViews() {
        arrangedLyricsRecyclerView = findViewById(R.id.arrangedLyricsRecyclerView);
        availableLyricsRecyclerView = findViewById(R.id.availableLyricsRecyclerView);
        submitButton = findViewById(R.id.submitButton);
        timerText = findViewById(R.id.timerText);
        difficultyText = findViewById(R.id.difficultyTextView);

        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton refreshButton = findViewById(R.id.refreshButton);

        backButton.setOnClickListener(v -> onBackPressed());
        refreshButton.setOnClickListener(v -> resetLyrics());

        // Update the UI with song name and artist
        TextView songNameTextView = findViewById(R.id.songNameTextView);
        TextView artistNameTextView = findViewById(R.id.artistNameTextView);

        songNameTextView.setText(songName);
        artistNameTextView.setText(artistName);
        difficultyText.setText(difficultyCapital);
    }

    @Override
    @SuppressWarnings("MissingSuperCall")
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Quit Game")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If user confirms, finish the game with a score of 0
                    if (timer != null) {
                        timer.cancel();
                    }
                    onGameComplete(0, 0, false);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false)
                .show();
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

        tickSoundPlayer = MediaPlayer.create(this, R.raw.tick_sound);

        timer = new CountDownTimer(GAME_DURATION, 1000) {
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerText.setText(String.format("%d:%02d", minutes, seconds));

                if (millisUntilFinished <= 10000) { // Last 10 seconds
                    if (tickSoundPlayer != null && !tickSoundPlayer.isPlaying()) {
                        tickSoundPlayer.start();
                    }
                }
            }

            public void onFinish() {
                timerText.setText("0:00");
                if (tickSoundPlayer != null) {
                    tickSoundPlayer.stop();
                    tickSoundPlayer.release();
                    tickSoundPlayer = null;
                }
                checkAnswer(true);
            }
        }.start();
    }

    private void initializeOriginalLyrics(UUID songId) {
        // Run lyrics fetching on a background thread
        Log.d("GameActivity", "Fetching lyrics for songId: " + songId);
        new Thread(() -> {
            LyricRepository lyricRepository = new LyricRepository(getApplicationContext());
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

    private String formatGameTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    private int calculateMaxPossibleScore() {
        int totalLyrics = originalLyrics.size();
        return totalLyrics * POINTS_PER_CORRECT_LINE * difficultyMultiplier;
    }

    private void onGameComplete(int totalTimeInSeconds, int timeBonus, boolean allCorrect) {
        // Prepare data for GameCompletionActivity
        String totalTime = formatGameTime(totalTimeInSeconds); // Format total time played
        int maxPossibleScore = calculateMaxPossibleScore();

        // Intent to start GameCompletionActivity
        Intent intent = new Intent(GameActivity.this, GameCompletionActivity.class);

        // Pass data via Intent
        intent.putExtra("songTitle", songName);
        intent.putExtra("artistName", artistName);
        intent.putExtra("totalScore", currentScore);
        intent.putExtra("correctLines", correctLines);
        intent.putExtra("allCorrect", allCorrect);
        intent.putExtra("timeBonus", timeBonus);
        intent.putExtra("totalTime", totalTime);
        intent.putExtra("maxPossibleScore", maxPossibleScore);

        // Start the activity
        startActivity(intent);

        // Finish GameActivity to prevent returning to it
        finish();
    }

    private void checkAnswer(boolean timeOut) {
        if (timer != null) {
            timer.cancel();
        }

        List<LyricLine> arrangedLyrics = arrangedAdapter.getLyrics();
        int totalPoints = 0;
        boolean allCorrect = true;

        // Check each lyric's position
        for (int i = 0; i < arrangedLyrics.size(); i++) {
            if (arrangedLyrics.get(i).getCorrectPosition() == i + 1) {
                totalPoints += POINTS_PER_CORRECT_LINE * difficultyMultiplier;
                correctLines++;
            } else {
                allCorrect = false;
            }
        }


        String[] timeParts = timerText.getText().toString().split(":");
        int remainingMinutes = Integer.parseInt(timeParts[0]);
        int remainingSeconds = Integer.parseInt(timeParts[1]);
        int elapsedSeconds = GAME_DURATION / 1000 - (remainingMinutes * 60 + remainingSeconds);

        int timeBonus = remainingSeconds * correctLines * difficultyMultiplier;
        totalPoints += timeBonus;

        currentScore += totalPoints;

        onGameComplete(elapsedSeconds, timeBonus, allCorrect);
        Log.d("GameActivity", "Total points: " + totalPoints);

        int finalTotalPoints = totalPoints;
        new Thread(() -> {
            // Get the user session and songId
            UserSession userSession = UserSession.getInstance(this);
            UUID userId = userSession.getUserId();
            UUID songId = UUID.fromString(getIntent().getStringExtra("songId")); // Get songId passed to this activity

            // Save the score to the database
            PlaySessionRepository playSessionRepository = new PlaySessionRepository(getApplicationContext());
            boolean scoreSaved = playSessionRepository.saveScore(songId, userId, finalTotalPoints);

            // Update the UI thread after saving the score
            runOnUiThread(() -> {
                if (!scoreSaved) {
                    Log.e("GameActivity", "Failed to save score to play session table");
                    Toast.makeText(this, "Failed to save score", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("GameActivity", "Score saved successfully.");
                }

            });
        }).start();  // Start the background thread
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupTimer();
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // Initialize the last values with first reading
        if (isFirstSensorReading) {
            lastX = x;
            lastY = y;
            lastZ = z;
            isFirstSensorReading = false;
            return;
        }

        // Calculate acceleration
        float acceleration = Math.abs(x + y + z - lastX - lastY - lastZ);

        lastX = x;
        lastY = y;
        lastZ = z;

        if (acceleration > SHAKE_THRESHOLD) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShakeTime > MIN_TIME_BETWEEN_SHAKES) {
                lastShakeTime = currentTime;
                handleShake();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed
    }

    private void handleShake() {
        // Play a vibration feedback
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 200 milliseconds
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        Toast.makeText(this, "Lyrics reset!", Toast.LENGTH_SHORT).show();

        // Reset the lyrics
        resetLyrics();
    }
}