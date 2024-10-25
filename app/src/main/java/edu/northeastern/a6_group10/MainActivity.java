package edu.northeastern.a6_group10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup connection to Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tempKey = database.getReference("temp");

        Button testFirebaseButton = findViewById(R.id.button_test_firebase);

        testFirebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Setting value in Firebase Database");
                Task<Void> t = tempKey.setValue("Hello, World! " + System.currentTimeMillis());
                t.addOnSuccessListener(aVoid -> {
                    Log.d("MainActivity", "Value set in Firebase Database");
                    Toast.makeText(MainActivity.this, "Value set in Firebase Database", Toast.LENGTH_SHORT).show();
                });
            }
        });

        Button atYourServiceButton = findViewById(R.id.at_your_service_button);

        atYourServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AtYourService.class);
                startActivity(intent);
            }
        });

        Button aboutMe = findViewById(R.id.button_about_me);

        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        });

        Button stickItToEm = findViewById(R.id.button_stick_it_to_em);

        stickItToEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StickItToEmActivity.class);
                startActivity(intent);
            }
        });
    }
}
