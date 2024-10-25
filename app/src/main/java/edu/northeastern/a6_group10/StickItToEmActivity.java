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

        MaterialButton loginButton = findViewById(R.id.loginButton);
        TextInputEditText usernameEditText = findViewById(R.id.usernameEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                if (username.isEmpty()) {
                    usernameEditText.setError("Username is required");
                    usernameEditText.requestFocus();
                } else {
                    Intent intent = new Intent(StickItToEmActivity.this, FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
