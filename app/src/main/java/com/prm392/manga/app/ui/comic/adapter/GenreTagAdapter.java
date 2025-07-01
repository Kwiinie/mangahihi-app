package com.prm392.manga.app.ui.comic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392.manga.app.R;

import java.util.List;

public class GenreTagAdapter extends RecyclerView.Adapter<GenreTagAdapter.GenreTagViewHolder> {

    private List<String> genres;

    public GenreTagAdapter(List<String> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre_tag, parent, false);
        return new GenreTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreTagViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.bind(genre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public void updateGenres(List<String> newGenres) {
        this.genres = newGenres;
        notifyDataSetChanged();
    }

    class GenreTagViewHolder extends RecyclerView.ViewHolder {
        private TextView txtGenre;

        public GenreTagViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGenre = itemView.findViewById(R.id.txt_genre);
        }

        public void bind(String genre) {
            txtGenre.setText(genre);
        }
    }
}
