package com.prm392.manga.app.ui.favorite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Favorite;
import com.prm392.manga.app.ui.comic.ComicDetailActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favoriteList;
    private Context context;

    public FavoriteAdapter(List<Favorite> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;
    }

    public void updateData(List<Favorite> newList) {
        favoriteList.clear();
        favoriteList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manga_card, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite item = favoriteList.get(position);

        holder.txtTitle.setText(item.getComicName());
        holder.txtType.setText("Yêu thích");
        holder.txtChapter.setText(""); // Bạn có thể thêm nếu có dữ liệu chapter mới nhất
        holder.txtViews.setText("");   // Bạn có thể thêm nếu API trả lượt xem

        Glide.with(context)
                .load(item.getCoverUrl())
                .placeholder(R.drawable.comic_cover_placeholder)
                .error(R.drawable.comic_cover_placeholder)
                .into(holder.imgCover);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = ComicDetailActivity.getStartIntent(context, item.getComicId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgCover;
        TextView txtType, txtTitle, txtChapter, txtViews;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imgCover = itemView.findViewById(R.id.manga_cover);
            txtType = itemView.findViewById(R.id.manga_type);
            txtTitle = itemView.findViewById(R.id.manga_title);
            txtChapter = itemView.findViewById(R.id.manga_chapter);
            txtViews = itemView.findViewById(R.id.manga_views);
        }
    }
}
