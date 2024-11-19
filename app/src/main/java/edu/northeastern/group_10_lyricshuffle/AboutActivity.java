package edu.northeastern.group_10_lyricshuffle;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup each team member card with the string email
        setupTeamMemberCard(R.id.rohanCard, getString(R.string.rohan_email));
        setupTeamMemberCard(R.id.rachanaCard, getString(R.string.rachana_email));
        setupTeamMemberCard(R.id.hariCard, getString(R.string.hari_email));
        setupTeamMemberCard(R.id.pavanCard, getString(R.string.pavan_email));
    }

    private void setupTeamMemberCard(int cardId, String email) {
        MaterialCardView card = findViewById(cardId);
        card.setOnClickListener(v -> copyEmailToClipboard(email));
    }

    private void copyEmailToClipboard(String email) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("email", email);
        clipboard.setPrimaryClip(clip);

        // Show a toast message to confirm the copy
        showCopiedToast();
    }

    private void showCopiedToast() {
        // Create and show a Snackbar instead of Toast for better user experience
        View rootView = findViewById(android.R.id.content);
        Snackbar.make(rootView, "Email copied to clipboard", Snackbar.LENGTH_SHORT).show();
    }
}