package com.example.recyclerviewlinear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<recycler_items> items;
    RecyclerView recyclerView;
    recycler_adapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerAdapter = new recycler_adapter(this, items);
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void loadData() {
        items = new ArrayList<>();
        items.add(new recycler_items("KitKat", "4.4"));
        items.add(new recycler_items("Lollipop", "5.0"));
        items.add(new recycler_items("Marshmallow", "6.0"));
    }
}