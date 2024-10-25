package edu.northeastern.a6_group10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.a6_group10.userListRecycler.ItemCard;
import edu.northeastern.a6_group10.userListRecycler.RviewAdapter;


public class UserProfileListActivity extends AppCompatActivity {

    private RviewAdapter rviewAdapter;
    private final ArrayList<ItemCard> itemList = new ArrayList<>();

    private EditText searchBox;
    private TextView textViewResults;
    private Spinner animeType;
    private Spinner animeRating;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService internetCheckExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean dbCheckRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_at_your_service);

        // Initialize views
        textViewResults = findViewById(R.id.loadingResultsTextView);

        textViewResults.setText("Connection Established");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState != null) {
            ArrayList<ItemCard> savedItemList = savedInstanceState.getParcelableArrayList("itemList");
            if (savedItemList != null) {
                itemList.addAll(savedItemList);
            }
        }

        init(savedInstanceState);
        startInternetCheck();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the background check when the activity is destroyed
        dbCheckRunning = false;
        internetCheckExecutor.shutdownNow();
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    //get user data from Firebase
    private String getUserList() throws Exception {
        return null;
    }

    private void fetchUserData(final String urlString) {
        executorService.execute(() -> {
            try {
                String result = getUserList();

                // Update the UI on the main thread
                mainThreadHandler.post(() -> {
                    if (result != null) {
                        parseAndDisplayUsers(result);
                        textViewResults.setText("Connection Established");
                    } else {
                        textViewResults.setText("Error retrieving data.");
                    }
                });
            } catch (Exception e) {
                // Handle exceptions
                mainThreadHandler.post(() -> textViewResults.setText("Failed to retrieve data: " + e.getMessage()));
            }
        });
    }

    //set itemList and notify adapter of dataset update
    private void parseAndDisplayUsers(String jsonResponse) {

    }

    private void initialItemData(Bundle savedInstanceState) {
        //Write fetching of data into state variables here
    }

    private void initiateRequest() {
        //set sticker image view and fetch user list from firebase

        // Build the query URL
        String baseUrl = null;

        Log.d("URL", baseUrl);
        fetchUserData(baseUrl);
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("itemList", itemList);
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager rLayoutManger = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.userListRecyclerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(itemList);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    // Method to start background internet check
    private void startInternetCheck() {
        internetCheckExecutor.execute(() -> {
            while (dbCheckRunning) {
                boolean isConnected = isRoutedToDB();
                mainThreadHandler.post(() -> {
                    if (isConnected) {
                        textViewResults.setText("Connection Established");
                    } else {
                        textViewResults.setText("Connection Lost");
                    }
                });
                try {
                    Thread.sleep(5000); // Check every 5 seconds
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    // Method to check connectivity to DB
    public static boolean isRoutedToDB() {
        return false;
    }
}