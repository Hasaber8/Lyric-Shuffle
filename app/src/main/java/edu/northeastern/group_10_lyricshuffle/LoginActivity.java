package edu.northeastern.group_10_lyricshuffle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.northeastern.group_10_lyricshuffle.model.User;
import edu.northeastern.group_10_lyricshuffle.service.AuthService;
import edu.northeastern.group_10_lyricshuffle.util.Result;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton;
    private TextView signUpButton;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = new AuthService();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton); // This is a TextView

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            // Run on background thread
            new Thread(() -> {
                Result<User> result = authService.login(email, password);

                runOnUiThread(() -> {
                    if (result.isSuccess()) {
                        // Store user session
                        // UserSession.getInstance().setCurrentUser(result.getData());

                        // Navigate to main activity
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Show error
                        Toast.makeText(this, result.getError(), Toast.LENGTH_LONG).show();
                    }
                });
            }).start();
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
