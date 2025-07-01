package com.prm392.manga.app.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private List<Genre> genres;
    private OnGenreClickListener listener;

    public interface OnGenreClickListener {
        void onGenreClick(Genre genre);
    }

    public GenreAdapter(List<Genre> genres, OnGenreClickListener listener) {
        this.genres = genres;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genres.get(position);
        holder.bind(genre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public void updateGenres(List<Genre> newGenres) {
        this.genres.clear();
        if (newGenres.size() > 10) {
            this.genres.addAll(newGenres.subList(0, 10));
        } else {
            this.genres.addAll(newGenres);
        }
        notifyDataSetChanged();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView genreIcon;
        private TextView genreName;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            genreIcon = itemView.findViewById(R.id.genre_icon);
            genreName = itemView.findViewById(R.id.genre_name);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onGenreClick(genres.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Genre genre) {
            genreName.setText(genre.getName());

            int iconRes = getGenreIcon(genre.getName());
            int colorRes = getGenreColor(genre.getName());

            genreIcon.setImageResource(iconRes);

            cardView.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), colorRes)
            );
        }

        private int getGenreIcon(String genreName) {
            switch (genreName.toLowerCase()) {
                case "action":
                    return R.drawable.ic_action;
                case "romance":
                    return R.drawable.ic_heart;
                case "comedy":
                    return R.drawable.ic_comedy;
                case "fantasy":
                    return R.drawable.ic_fantasy;
                case "drama":
                    return R.drawable.ic_drama;
                case "adventure":
                    return R.drawable.ic_adventure;
                case "school life":
                    return R.drawable.ic_school;
                case "shoujo":
                    return R.drawable.ic_shoujo;
                case "slice of life":
                    return R.drawable.ic_slice_life;
                case "supernatural":
                    return R.drawable.ic_supernatural;
                default:
                    return R.drawable.ic_book;
            }
        }

        private int getGenreColor(String genreName) {
            switch (genreName.toLowerCase()) {
                case "action":
                    return R.color.genre_action;
                case "romance":
                    return R.color.genre_romance;
                case "comedy":
                    return R.color.genre_comedy;
                case "fantasy":
                    return R.color.genre_fantasy;
                case "drama":
                    return R.color.genre_drama;
                case "adventure":
                    return R.color.genre_adventure;
                case "school life":
                    return R.color.genre_school;
                case "shoujo":
                    return R.color.genre_shoujo;
                case "slice of life":
                    return R.color.genre_slice_life;
                case "supernatural":
                    return R.color.genre_supernatural;
                default:
                    return R.color.accent_blue;
            }
        }
    }
}