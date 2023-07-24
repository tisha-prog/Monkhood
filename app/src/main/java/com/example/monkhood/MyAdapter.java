package com.example.monkhood;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> dataList;
    private SharedPreferences sharedPreferences;

    public MyAdapter(List<String> dataList, SharedPreferences sharedPreferences) {
        this.dataList = dataList;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your view holder layout here
        // ...
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to your views here
        String dataItem = dataList.get(position);
        holder.textView.setText(dataItem);

        // Retrieve data from SharedPreferences and use it in the adapter
        String savedData = sharedPreferences.getString("name", "");
        holder.textView.setText(savedData);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize your views here
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
