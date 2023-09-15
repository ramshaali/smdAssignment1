package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    private ListView searchResultsListView;
    private TextView recent;
    private TextView popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchResultsListView = findViewById(R.id.searchResultsListView);


        // Create mock data for recent and popular searches
        List<String> recentSearches = new ArrayList<>();
        recentSearches.add("Item number 1");
        recentSearches.add("Item number 2");

        List<String> popularSearches = new ArrayList<>();
        popularSearches.add("Item number 1");
        popularSearches.add("Item number 2");

        // Create an ArrayAdapter to populate the ListView
        ArrayAdapter<String> searchResultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        // Add headings for recent and popular searches
        searchResultsAdapter.add("Recent Searches");
        searchResultsAdapter.addAll(recentSearches);
        searchResultsAdapter.add("Popular Searches");
        searchResultsAdapter.addAll(popularSearches);

        // Set the adapter for the ListView
        searchResultsListView.setAdapter(searchResultsAdapter);

        searchResultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Check if the clicked item is not a heading
                if (position > 0) {
                    // Start the NextActivity
                    Intent intent = new Intent(search.this, searchresults.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void onSearchBarClick(View view) {
        if (searchResultsListView.getVisibility() == View.GONE) {
            searchResultsListView.setVisibility(View.VISIBLE); // Show the ListView
        } else {
            searchResultsListView.setVisibility(View.GONE); // Hide the ListView
        }
    }




}