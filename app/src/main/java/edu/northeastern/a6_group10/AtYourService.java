package edu.northeastern.a6_group10;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.a6_group10.recycler.ItemCard;
import edu.northeastern.a6_group10.recycler.RviewAdapter;

public class AtYourService extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;
    private RecyclerView.LayoutManager rLayoutManger;
    private ArrayList<ItemCard> itemList = new ArrayList<>();

    private EditText searchBox;
    private Spinner animeType;
    private Spinner animeRating;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_at_your_service);

        searchBox = findViewById(R.id.searchBox);
        animeType = findViewById(R.id.animeTypeDropdown);
        animeRating = findViewById(R.id.animeRatingDropdown);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateRequest();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }


    private void initialItemData(Bundle savedInstanceState) {
        //Write fetching of data into state variables here
    }

    /*
    To create the thread to make http calls
     */
    private void initiateRequest(){

    }

    public void handleAnimeTextQuery(){

    }

    public void handleAnimeTypeQuery(){

    }

    public void handleLimitUpdate(){

    }

    public void handleRatingQuery(){

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Fill this out to save state for configuration update
    }

    private void createRecyclerView() {


        rLayoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.outputListRecyclerView);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(itemList);


        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManger);


    }
}