package com.example.recyclerview4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<RecyclerItems> items;

    RecyclerView recyclerView;

    RecyclerAdapter recyclerAdapter;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        loadItems();

        recyclerView = findViewById(R.id.recycler);
        recyclerAdapter = new RecyclerAdapter(this, items);

        searchView = findViewById(R.id.search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newList(newText);
                return true;
            }
        });
    }

    private void newList(String newText) {
        ArrayList<RecyclerItems> newItems = new ArrayList<>();

        for(RecyclerItems items2 : items){
            if(items2.getName().toLowerCase().contains(newText.toLowerCase())){
                newItems.add(items2);
            }
        }

        recyclerAdapter.setItems(newItems);
    }

    private void loadItems() {
        items.add(new RecyclerItems("Liberty Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Josua Elico", "20"));
        items.add(new RecyclerItems("John Anthony Elico", "20"));
        items.add(new RecyclerItems("Kimberly Elico", "20"));
        items.add(new RecyclerItems("Clark Elico", "20"));
        items.add(new RecyclerItems("Jake Elico", "20"));
        items.add(new RecyclerItems("Laylay Elico", "20"));
    }
}