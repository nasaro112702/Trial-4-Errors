package com.example.recyclerviewlinear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.MyHolder> {

    Context context;
    ArrayList<recycler_items> items;

    public recycler_adapter(Context context, ArrayList<recycler_items> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public recycler_adapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycler, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recycler_adapter.MyHolder holder, int position) {
        holder.version_name.setText(items.get(position).getVersion_name());
        holder.version.setText(items.get(position).getVersion_());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.get(holder.getPosition()).getVersion_name()+" Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView version_name, version;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            version_name = itemView.findViewById(R.id.version_name);
            version = itemView.findViewById(R.id.version);
        }
    }
}
