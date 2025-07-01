package com.prm392.manga.app.ui.comic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Chapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapters;
    private OnChapterClickListener listener;
    private SimpleDateFormat displayFormat;
    private SimpleDateFormat parseFormat;

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);
    }

    public ChapterAdapter(List<Chapter> chapters, OnChapterClickListener listener) {
        this.chapters = chapters;
        this.listener = listener;
        this.displayFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        this.parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chapter, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.bind(chapter);
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    public void updateChapters(List<Chapter> newChapters) {
        this.chapters = newChapters;
        notifyDataSetChanged();
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChapterTitle;
        private TextView txtChapterDate;
        private View itemView;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            txtChapterTitle = itemView.findViewById(R.id.txt_chapter_title);
            txtChapterDate = itemView.findViewById(R.id.txt_chapter_date);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onChapterClick(chapters.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Chapter chapter) {
            txtChapterTitle.setText(chapter.getTitle());

            if (chapter.getCreatedAt() != null) {
                try {
                    Date dateComic = parseFormat.parse(chapter.getCreatedAt());
                    txtChapterDate.setText(displayFormat.format(dateComic));
                } catch (ParseException e) {
                    txtChapterDate.setText("Không rõ");
                }
            } else {
                txtChapterDate.setText("Không rõ");
            }

        }

        private boolean isChapterNew(Chapter chapter) {
            if (chapter.getCreatedAt() == null) return false;

            try {
                Date chapterDate = parseFormat.parse(chapter.getCreatedAt());
                Date now = new Date();
                long diffInMillis = now.getTime() - chapterDate.getTime();
                long diffInDays = diffInMillis / (1000 * 60 * 60 * 24);
                return diffInDays <= 7;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}