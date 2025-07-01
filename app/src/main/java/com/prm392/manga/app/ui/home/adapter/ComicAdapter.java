package com.prm392.manga.app.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Comic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MangaViewHolder> {

    private List<Comic> comics;
    private OnMangaClickListener listener;
    private boolean showTime = false;

    public interface OnMangaClickListener {
        void onMangaClick(Comic comic);
    }

    public ComicAdapter(List<Comic> comics, OnMangaClickListener listener) {
        this.comics = comics;
        this.listener = listener;
    }

    public ComicAdapter(List<Comic> comics, OnMangaClickListener listener, boolean showTime) {
        this.comics = comics;
        this.listener = listener;
        this.showTime = showTime;
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manga_card, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Comic comic = comics.get(position);
        holder.bind(comic, showTime);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public void updateComics(List<Comic> newComics) {
        this.comics.clear();
        this.comics.addAll(newComics);
        notifyDataSetChanged();
    }

    public void addComics(List<Comic> newComics) {
        int startPosition = this.comics.size();
        this.comics.addAll(newComics);
        notifyItemRangeInserted(startPosition, newComics.size());
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
        notifyDataSetChanged();
    }

    class MangaViewHolder extends RecyclerView.ViewHolder {
        private ImageView mangaCover;
        private TextView mangaTitle;
        private TextView mangaChapter;
        private TextView mangaViews;
        private TextView mangaTime;

        private TextView mangaType;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);

            mangaCover = itemView.findViewById(R.id.manga_cover);
            mangaTitle = itemView.findViewById(R.id.manga_title);
            mangaChapter = itemView.findViewById(R.id.manga_chapter);
            mangaViews = itemView.findViewById(R.id.manga_views);
            mangaTime = itemView.findViewById(R.id.manga_time);
            mangaType = itemView.findViewById(R.id.manga_type);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onMangaClick(comics.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Comic comic, boolean showTime) {
            mangaTitle.setText(comic.getName());
            mangaChapter.setText(comic.getLatestChapter());
            mangaViews.setText(formatViews(comic.getView()) + " views");

            mangaType.setText(comic.getType());

            if (showTime) {
                mangaTime.setVisibility(View.VISIBLE);
                mangaTime.setText(getTimeAgo(comic.getUpdatedAt()));
            } else {
                mangaTime.setVisibility(View.GONE);
            }

            String coverUrl = comic.getCoverUrl();
            if (coverUrl == null || coverUrl.isEmpty()) {
                coverUrl = "https://via.placeholder.com/300x400";
            }

            Glide.with(itemView.getContext())
                    .load(coverUrl)
                    .placeholder(R.drawable.comic_cover_placeholder)
                    .error(R.drawable.comic_cover_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(mangaCover);
        }

        private String formatViews(int views) {
            if (views >= 1000000) {
                return String.format(Locale.getDefault(), "%.1fM", views / 1000000.0);
            } else if (views >= 1000) {
                return String.format(Locale.getDefault(), "%.1fK", views / 1000.0);
            } else {
                return String.valueOf(views);
            }
        }

        private String getTimeAgo(String dateString) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date date = sdf.parse(dateString);
                if (date == null) return "Unknown";

                long timeAgo = System.currentTimeMillis() - date.getTime();

                if (timeAgo < TimeUnit.MINUTES.toMillis(1)) {
                    return "Just now";
                } else if (timeAgo < TimeUnit.HOURS.toMillis(1)) {
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(timeAgo);
                    return minutes + "m ago";
                } else if (timeAgo < TimeUnit.DAYS.toMillis(1)) {
                    long hours = TimeUnit.MILLISECONDS.toHours(timeAgo);
                    return hours + "h ago";
                } else if (timeAgo < TimeUnit.DAYS.toMillis(7)) {
                    long days = TimeUnit.MILLISECONDS.toDays(timeAgo);
                    return days + "d ago";
                } else {
                    return "1w+ ago";
                }
            } catch (ParseException e) {
                return "Unknown";
            }
        }
    }
}
