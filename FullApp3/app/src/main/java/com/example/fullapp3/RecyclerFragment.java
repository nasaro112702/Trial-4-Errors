package com.example.fullapp3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerFragment newInstance(String param1, String param2) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<RecyclerItems> items;
    RecyclerView recyclerView;

    RecyclerAdapter recyclerAdapter;

    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        loadItems();
        recyclerView = view.findViewById(R.id.recycler);
        searchView = view.findViewById(R.id.search);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerAdapter = new RecyclerAdapter(getContext(), items);

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

        // Inflate the layout for this fragment
        return view;
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
        items = new ArrayList<>();
        items.add(new RecyclerItems("Ryan Elico", "20"));
        items.add(new RecyclerItems("Ian Elico", "22"));
        items.add(new RecyclerItems("Rico Elico", "20"));
        items.add(new RecyclerItems("Liberty Elico", "22"));
        items.add(new RecyclerItems("Josua Elico", "20"));
        items.add(new RecyclerItems("John Anthony Elico", "22"));
        items.add(new RecyclerItems("Kimberly Elico", "20"));
        items.add(new RecyclerItems("Clark Elico", "22"));
        items.add(new RecyclerItems("Jake Elico", "20"));
        items.add(new RecyclerItems("Arnel Elico", "22"));
        items.add(new RecyclerItems("Edito Elico", "20"));
        items.add(new RecyclerItems("Emelita Elico", "22"));
        items.add(new RecyclerItems("Julieta Elico", "20"));
    }
}