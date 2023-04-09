package com.example.recyclerview3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<RecyclerItems> items;
    RecyclerView recyclerView;

    SearchView searchView;

    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadItems();

        recyclerView = findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(this, items);

        recyclerView.setAdapter(recyclerAdapter);

        searchView = findViewById(R.id.search);

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

        for(RecyclerItems items1 : items){
            if(items1.getName().toLowerCase().contains(newText.toLowerCase())){
                newItems.add(items1);
            }
        }

        recyclerAdapter.setItems(newItems);
    }

    private void loadItems() {
        items = new ArrayList<>();
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "20"));
        items.add(new RecyclerItems("Rico Elico", "20"));
    }
}