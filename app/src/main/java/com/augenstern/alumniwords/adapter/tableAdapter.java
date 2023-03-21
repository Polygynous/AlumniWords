package com.augenstern.alumniwords.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.augenstern.alumniwords.R;

import java.util.ArrayList;

public class tableAdapter extends RecyclerView.Adapter<tableAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> Objects;

    public tableAdapter(Context context, ArrayList<String> objects) {
        mContext = context;
        Objects = objects;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.table_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_table.setText(Objects.get(position));
    }

    @Override
    public int getItemCount() {
        return Objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView tv_table;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rootView = itemView;
            this.tv_table = (TextView) rootView.findViewById(R.id.tv_table);
        }
    }

}
