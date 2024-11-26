package edu.northeastern.group_10_lyricshuffle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.northeastern.group_10_lyricshuffle.model.User;
import edu.northeastern.group_10_lyricshuffle.service.AuthService;
import edu.northeastern.group_10_lyricshuffle.util.NotificationScheduler;
import edu.northeastern.group_10_lyricshuffle.util.Result;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton;
    private TextView signUpButton;

    private AuthService authService;

    private static final String TAG = "NotificationPermission";
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Check notification permission
        checkNotificationPermission();

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
//                         UserSession.getInstance().setCurrentUser(result.getData());
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userId", result.getData().getUserId().toString());
                        editor.putString("username", result.getData().getUsername());
                        editor.putString("email", result.getData().getEmail());
                        editor.apply();
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

    private void checkNotificationPermission() {
        // Check if running on Android 13 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Check if permission is not granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.POST_NOTIFICATIONS)) {
                    // Show explanation to user
                    showNotificationPermissionRationale();
                } else {
                    // No explanation needed, request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_NOTIFICATION_PERMISSION);
                }
            } else {
                // Permission already granted, proceed with notification setup
                initializeNotifications();
            }
        } else {
            // For Android 12 and below, no runtime permission needed
            initializeNotifications();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void showNotificationPermissionRationale() {
        new AlertDialog.Builder(this)
                .setTitle("Notification Permission")
                .setMessage("LyricShuffle needs notification permission to remind you to play at the best times. Please grant notification permission in Settings.")
                .setPositiveButton("Grant Permission", (dialog, which) -> {
                    ActivityCompat.requestPermissions(LoginActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_NOTIFICATION_PERMISSION);
                })
                .setNegativeButton("Not Now", null)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                initializeNotifications();
            } else {
                // Permission denied
                showNotificationPermissionDeniedDialog();
            }
        }
    }

    private void showNotificationPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Notifications Disabled")
                .setMessage("To get reminders to play LyricShuffle, please enable notifications in Settings.")
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Not Now", null)
                .show();
    }

    private void initializeNotifications() {
        Log.d(TAG, "Initializing notifications");
        NotificationScheduler scheduler = new NotificationScheduler(this);

        // Check if notifications are enabled before scheduling
        if (scheduler.areNotificationsEnabled()) {
            Log.d(TAG, "Notifications are enabled, scheduling notifications");
            scheduler.scheduleAllNotifications();
        } else {
            Log.d(TAG, "Notifications are not enabled");
            showNotificationPermissionDeniedDialog();
        }
    }
}
