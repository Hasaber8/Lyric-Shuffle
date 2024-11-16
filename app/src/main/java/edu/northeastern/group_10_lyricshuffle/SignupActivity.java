package edu.northeastern.group_10_lyricshuffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import edu.northeastern.group_10_lyricshuffle.model.User;
import edu.northeastern.group_10_lyricshuffle.service.AuthService;
import edu.northeastern.group_10_lyricshuffle.util.Result;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private TextInputEditText firstNameField;
    private TextInputEditText lastNameField;
    private TextInputEditText usernameField;
    private TextInputEditText emailField;
    private TextInputEditText passwordField;
    private MaterialButton signupButton;
    private TextView loginLink;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize services
        authService = new AuthService();

        // Initialize views
        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signupButton = findViewById(R.id.signupButton);
        loginLink = findViewById(R.id.loginLink);

        // Set up click listeners
        setupClickListeners();
    }

    private void setupClickListeners() {
        // Sign up button click
        signupButton.setOnClickListener(v -> handleSignup());

        // Login link click
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleSignup() {
        // Get input values
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String username = usernameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();

        // Log input values
        Log.d(TAG, "First Name: " + firstName + ", Last Name: " + lastName +
                ", Username: " + username + ", Email: " + email + ", Password: " + password);

        // Validate input
        if (!validateInput(firstName, lastName, username, email, password)) {
            return;
        }

        // Show loading state
        setLoading(true);

        // Perform signup on background thread
        new Thread(() -> {
            try {
                // Create username from first and last name if not provided
                if (username.isEmpty()) {
                    Toast.makeText(SignupActivity.this,
                            "Username is required", Toast.LENGTH_LONG).show();
                }

                // Attempt signup
                Result<User> result = authService.signUp(username, email, password);

                // Handle result on UI thread
                runOnUiThread(() -> {
                    setLoading(false);
                    if (result.isSuccess()) {
                        onSignupSuccess(result.getData());
                    } else {
                        onSignupFailure(result.getError());
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error during signup", e);
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(SignupActivity.this,
                            "An unexpected error occurred", Toast.LENGTH_LONG).show();
                });
            }
        }).start();
    }

    private boolean validateInput(String firstName, String lastName, String username,
                                  String email, String password) {
        // Clear previous errors
        clearErrors();

        boolean isValid = true;

        // Validate first name
        if (firstName.isEmpty()) {
            showError(firstNameField, "First name is required");
            isValid = false;
        }

        // Validate last name
        if (lastName.isEmpty()) {
            showError(lastNameField, "Last name is required");
            isValid = false;
        }

        // Validate email
        if (email.isEmpty()) {
            showError(emailField, "Email is required");
            isValid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(emailField, "Enter a valid email address");
            isValid = false;
        }

        // Validate password
        if (password.isEmpty()) {
            showError(passwordField, "Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            showError(passwordField, "Password must be at least 8 characters");
            isValid = false;
        }

        return isValid;
    }

    private void showError(TextInputEditText field, String error) {
        TextInputLayout layout = (TextInputLayout) field.getParent().getParent();
        layout.setError(error);
    }

    private void clearErrors() {
        ((TextInputLayout) firstNameField.getParent().getParent()).setError(null);
        ((TextInputLayout) lastNameField.getParent().getParent()).setError(null);
        ((TextInputLayout) usernameField.getParent().getParent()).setError(null);
        ((TextInputLayout) emailField.getParent().getParent()).setError(null);
        ((TextInputLayout) passwordField.getParent().getParent()).setError(null);
    }

    private void setLoading(boolean isLoading) {
        signupButton.setEnabled(!isLoading);
        signupButton.setText(isLoading ? "Creating Account..." : "Sign Up");

        // Disable input fields while loading
        firstNameField.setEnabled(!isLoading);
        lastNameField.setEnabled(!isLoading);
        usernameField.setEnabled(!isLoading);
        emailField.setEnabled(!isLoading);
        passwordField.setEnabled(!isLoading);
    }

    private void onSignupSuccess(User user) {
        // Store user session
        // UserSession.getInstance().setCurrentUser(user);

        // Show success message
        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        // Navigate to main activity
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void onSignupFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
