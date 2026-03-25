package com.example.myapplication.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.ScoreView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<ScoreView> list;

    public ScoreAdapter(List<ScoreView> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreView item = list.get(position);
        holder.tvName.setText(item.name);
        holder.tvSubject.setText(item.subject);
        holder.tvScore.setText(String.valueOf(item.score));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSubject, tvScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
