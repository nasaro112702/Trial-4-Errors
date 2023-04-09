package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

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
        ArrayList<RecyclerItems> itemsList = new ArrayList<>();

        for(RecyclerItems iterateItems : items){
            if(iterateItems.getName().toLowerCase().contains(newText.toLowerCase())){
                itemsList.add(iterateItems);
            }
        }
        recyclerAdapter.setItems(itemsList);

    }


    private void loadItems() {
        items = new ArrayList<>();

        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Nessa Grace Manlapaz", "20"));
        items.add(new RecyclerItems("Franz Mozar", "20"));

    }
}