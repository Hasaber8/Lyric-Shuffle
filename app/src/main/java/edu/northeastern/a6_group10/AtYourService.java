package edu.northeastern.a6_group10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.northeastern.a6_group10.recycler.ItemCard;
import edu.northeastern.a6_group10.recycler.RviewAdapter;

import java.io.IOException;

public class AtYourService extends AppCompatActivity {

    private RviewAdapter rviewAdapter;
    private final ArrayList<ItemCard> itemList = new ArrayList<>();

    private EditText searchBox;
    private TextView textViewResults;
    private Spinner animeType;
    private TextView loadingResultsTextView;
    private Spinner animeRating;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService internetCheckExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean internetCheckRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_at_your_service);

        // Initialize views
        searchBox = findViewById(R.id.searchBox);
        animeType = findViewById(R.id.animeTypeDropdown);
        animeRating = findViewById(R.id.animeRatingDropdown);
        Button searchButton = findViewById(R.id.searchButton);
        textViewResults = findViewById(R.id.loadingResultsTextView);

        textViewResults.setText("Connection Established");

        // Set the search button's click listener
        searchButton.setOnClickListener(v -> initiateRequest());

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
        internetCheckRunning = false;
        internetCheckExecutor.shutdownNow();
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private String getAnimeData(String urlString) throws Exception {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);  // 10 seconds for connection timeout
            urlConnection.setReadTimeout(15000);     // 15 seconds for read timeout
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("HTTP error code: " + responseCode);
            }

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            return buffer.toString();
        } catch (java.net.SocketTimeoutException e) {
            throw new Exception("Connection timed out. Please try again later.", e);

        } catch (java.net.UnknownHostException e) {
            throw new Exception("Unable to connect to the server. Check your internet connection.", e);

        } catch (Exception e) {
            throw new Exception("An error occurred while retrieving data: " + e.getMessage(), e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    private void fetchAnimeData(final String urlString) {
        executorService.execute(() -> {
            try {
                String result = getAnimeData(urlString);

                // Update the UI on the main thread
                mainThreadHandler.post(() -> {
                    if (result != null) {
                        parseAndDisplayAnime(result);
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

    private void parseAndDisplayAnime(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray animeArray = jsonObject.getJSONArray("data");

            itemList.clear();

            for (int i = 0; i < animeArray.length(); i++) {
                JSONObject animeObject = animeArray.getJSONObject(i);
                String title = animeObject.getString("title");
                String rating = animeObject.optString("rating", "Unknown");  // Handle possible missing fields
                String type = animeObject.optString("type", "Unknown");
                String imageUrl = animeObject.getJSONObject("images").getJSONObject("jpg").getString("image_url");

                // Add the new item to the list
                itemList.add(new ItemCard(title, rating, imageUrl, type));
            }

            // Notify the adapter that the data has changed
            rviewAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            textViewResults.setText("Error parsing data.");
        }
    }

    private void initialItemData(Bundle savedInstanceState) {
        //Write fetching of data into state variables here
    }

    private void initiateRequest() {
        String searchQuery = handleAnimeTextQuery();
        String typeQuery = handleAnimeTypeQuery();
        String ratingQuery = handleRatingQuery();

        textViewResults.setText("Loading Results");

        // Build the query URL
        String baseUrl = "https://api.jikan.moe/v4/anime?sfw=true&q=" + searchQuery;
        if (!typeQuery.equals("All")) {
            baseUrl += "&type=" + typeQuery;
        }
        if (!ratingQuery.equals("All")) {
            baseUrl += "&rating=" + ratingQuery;
        }

        // Fetch data from the API
        Log.d("URL", baseUrl);
        fetchAnimeData(baseUrl);
    }

    public String handleAnimeTextQuery() {
        return searchBox.getText().toString().trim();
    }

    public String handleAnimeTypeQuery() {
        return animeType.getSelectedItem().toString();
    }

    public String handleRatingQuery() {
        return animeRating.getSelectedItem().toString();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("itemList", itemList);
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager rLayoutManger = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.outputListRecyclerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(itemList);

        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);
    }

    // Method to start background internet check
    private void startInternetCheck() {
        internetCheckExecutor.execute(() -> {
            while (internetCheckRunning) {
                boolean isConnected = isRoutedToInternet();
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

    // Method to check internet connection
    public static boolean isRoutedToInternet() {
        try {
            HttpURLConnection con = (HttpURLConnection)
                    new URL("https://www.google.com").openConnection();
            con.setRequestProperty("User-Agent", "Android");
            con.setRequestProperty("Connection", "close");
            con.setConnectTimeout(1500);
            con.connect();
            return con.getResponseCode() == 200 || con.getResponseCode() == 204;
        } catch (IOException e) {
            return false;
        }
    }
}