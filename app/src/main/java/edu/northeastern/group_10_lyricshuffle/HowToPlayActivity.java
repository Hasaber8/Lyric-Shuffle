package edu.northeastern.group_10_lyricshuffle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.group_10_lyricshuffle.adapter.LyricsAdapter;

public class HowToPlayActivity extends AppCompatActivity {

    private RecyclerView arrangedLyricsRecyclerView;
    private RecyclerView availableLyricsRecyclerView;
    private MaterialButton submitButton;
    private TextView tutorialInstructions;
    private List<String> availableLyrics = new ArrayList<>();
    private List<String> arrangedLyrics = new ArrayList<>();
    private int currentStep = 0; // Tracks the step in the tutorial
    private final List<String> correctOrder = Arrays.asList("If you could see that I'm the one who understands you",
            "Been here all along, so why can't you see?",
            "You belong with me"); // Expected order

    private LyricsAdapter availableAdapter;
    private LyricsAdapter arrangedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        // Initialize views
        arrangedLyricsRecyclerView = findViewById(R.id.arrangedLyricsRecyclerView);
        availableLyricsRecyclerView = findViewById(R.id.availableLyricsRecyclerView);
        submitButton = findViewById(R.id.submitButton);
        tutorialInstructions = findViewById(R.id.tutorialInstructions);

        // Initialize RecyclerViews
        setupRecyclerViews();

        // Start tutorial
        setupTutorial();
    }

    private void setupRecyclerViews() {
        // Set layout managers
        arrangedLyricsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        availableLyricsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapters
        arrangedAdapter = new LyricsAdapter(arrangedLyrics, false);
        availableAdapter = new LyricsAdapter(availableLyrics, true);

        arrangedLyricsRecyclerView.setAdapter(arrangedAdapter);
        availableLyricsRecyclerView.setAdapter(availableAdapter);

        // Set listener for lyric selection
        availableAdapter.setOnLyricClickListener(this::onLyricSelected);
    }

    private void setupTutorial() {
        // Populate available lyrics
        availableLyrics.addAll(
                Arrays.asList("You belong with me",
                        "If you could see that I'm the one who understands you",
                        "Been here all along, so why can't you see?"
                        )
        );
        availableAdapter.notifyDataSetChanged();

        // Disable submit button initially
        submitButton.setEnabled(false);

        // Set click listener for submit button
        submitButton.setOnClickListener(v -> {
            showCompletionDialog();
        });

        // Highlight the first step
        updateStep();
    }

    private void onLyricSelected(String lyric) {
        if (lyric.equals(correctOrder.get(currentStep))) {
            // Correct lyric: Add it to arranged section
            arrangedLyrics.add(lyric);
            arrangedAdapter.notifyDataSetChanged();

            // Remove lyric from available section
            availableLyrics.remove(lyric);
            availableAdapter.notifyDataSetChanged();

            // Move to the next step
            currentStep++;
            updateStep();
        } else {
            // Incorrect lyric: Show snackbar
            Snackbar.make(availableLyricsRecyclerView, "Please select the correct lyric.\nHint: It is the highlighted one here. We got you, soldier!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void updateStep() {
        if (currentStep < correctOrder.size()) {
            // Highlight correct lyric
            availableAdapter.setHighlight(correctOrder.get(currentStep));

            // Update instruction text
            String instruction = "Select the correct lyric for line " + (currentStep + 1);
            tutorialInstructions.setText(instruction);
        } else {
            // All lyrics are selected
            submitButton.setEnabled(true);
            tutorialInstructions.setText("All lyrics are arranged! Submit your order.");
        }
    }

    private void showCompletionDialog() {
        // Show a dialog box on tutorial completion
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tutorial Complete!")
                .setMessage("🎉 You've got them all, champ! Now go ahead and compete with real users!")
                .setIcon(R.drawable.ic_trophy) // Add your trophy icon drawable
                .setPositiveButton("OK", (dialog, which) -> {
                    // Navigate back to HomeActivity
                    finish(); // Or implement navigation logic here
                })
                .setCancelable(false)
                .show();
    }
}
