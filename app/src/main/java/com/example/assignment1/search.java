package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
    }

    public void onSearchBarClick(View view) {
        if (searchResultsListView.getVisibility() == View.GONE) {
            searchResultsListView.setVisibility(View.VISIBLE); // Show the ListView
        } else {
            searchResultsListView.setVisibility(View.GONE); // Hide the ListView
        }
    }
}