package edu.northeastern.group_10_lyricshuffle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is the main activity of the app(the main menu)
        // here we will have a check to open the login page if there is no user logged in
        // or open the home page if the user is already logged in
    }
}