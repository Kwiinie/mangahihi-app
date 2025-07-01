package com.prm392.manga.app.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.prm392.manga.app.R;

import java.util.List;

public class ComicTypeAdapter extends RecyclerView.Adapter<ComicTypeAdapter.ComicTypeViewHolder> {

    private List<String> comicTypes;
    private OnComicTypeClickListener listener;

    public interface OnComicTypeClickListener {
        void onComicTypeClick(String comicType);
    }

    public ComicTypeAdapter(List<String> comicTypes, OnComicTypeClickListener listener) {
        this.comicTypes = comicTypes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ComicTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comic_type, parent, false);
        return new ComicTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicTypeViewHolder holder, int position) {
        String comicType = comicTypes.get(position);
        holder.bind(comicType);
    }

    @Override
    public int getItemCount() {
        return comicTypes.size();
    }

    class ComicTypeViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView comicTypeTitle;
        private TextView comicTypeSubtitle;

        public ComicTypeViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            comicTypeTitle = itemView.findViewById(R.id.comic_type_title);
            comicTypeSubtitle = itemView.findViewById(R.id.comic_type_subtitle);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onComicTypeClick(comicTypes.get(getAdapterPosition()));
                }
            });
        }

        public void bind(String comicType) {
            comicTypeTitle.setText(comicType);
            comicTypeSubtitle.setText(getSubtitle(comicType));

            int backgroundDrawable = getBackgroundDrawable(comicType);
            cardView.setBackgroundResource(backgroundDrawable);

            comicTypeTitle.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
            comicTypeSubtitle.setTextColor(itemView.getContext().getResources().getColor(android.R.color.white));
        }

        private String getSubtitle(String comicType) {
            switch (comicType.toLowerCase()) {
                case "manga":
                    return "Truyện tranh Nhật Bản";
                case "manhwa":
                    return "Truyện tranh Hàn Quốc";
                case "manhua":
                    return "Truyện tranh Trung Quốc";
                case "dc comics":
                    return "Siêu anh hùng DC";
                case "marvel comics":
                    return "Siêu anh hùng Marvel";
                default:
                    return "Khám phá thế giới truyện tranh";
            }
        }

        private int getBackgroundDrawable(String comicType) {
            switch (comicType.toLowerCase()) {
                case "manga":
                    return R.drawable.comic_type_manga_background;
                case "manhwa":
                    return R.drawable.comic_type_manhwa_background;
                case "manhua":
                    return R.drawable.comic_type_manhua_background;
                case "dc comics":
                    return R.drawable.comic_type_dc_background;
                case "marvel comics":
                    return R.drawable.comic_type_marvel_background;
                default:
                    return R.drawable.comic_type_manga_background;
            }
        }
    }
}