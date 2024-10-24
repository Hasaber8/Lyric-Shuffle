package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class StickItToEmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick_it_to_em); // Your XML layout

        // Match with the IDs in the XML
        MaterialButton loginButton = findViewById(R.id.loginButton);
        TextInputEditText usernameEditText = findViewById(R.id.usernameEditText);

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the username is empty
                String username = usernameEditText.getText().toString().trim();
                if (username.isEmpty()) {
                    // Show an error message if the username is not entered
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();  // Focus the input field
                } else {
                    // If username is entered, proceed to the next activity
                    Intent intent = new Intent(StickItToEmActivity.this, FeedActivity.class);
                    startActivity(intent); // Start the new activity
                }
            }
        });
    }
}
