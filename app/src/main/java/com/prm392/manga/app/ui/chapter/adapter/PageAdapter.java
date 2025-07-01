package com.prm392.manga.app.ui.chapter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Page;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageViewHolder> {

    private List<Page> pages;
    private Context context;
    private OnPageClickListener listener;
    private boolean showPageNumbers = false;
    private ReadingMode readingMode = ReadingMode.FIT_WIDTH;

    public enum ReadingMode {
        FIT_WIDTH,
        FIT_HEIGHT,
        ORIGINAL_SIZE,
        FIT_SCREEN
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    public PageAdapter(Context context, List<Page> pages) {
        this.context = context;
        this.pages = pages != null ? pages : new ArrayList<>();
    }

    public void setOnPageClickListener(OnPageClickListener listener) {
        this.listener = listener;
    }

    public void setShowPageNumbers(boolean show) {
        this.showPageNumbers = show;
        notifyDataSetChanged();
    }

    public void setReadingMode(ReadingMode mode) {
        this.readingMode = mode;
        notifyDataSetChanged();
    }

    public void updatePages(List<Page> newPages) {
        this.pages.clear();
        if (newPages != null) {
            this.pages.addAll(newPages);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_page, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        Page page = pages.get(position);
        holder.bind(page, position);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class PageViewHolder extends RecyclerView.ViewHolder {
        private PhotoView imgPage;
        private ProgressBar progressBar;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPage = itemView.findViewById(R.id.img_page);
            progressBar = itemView.findViewById(R.id.progress_bar);

            imgPage.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPageClick(getAdapterPosition());
                }
            });

        }

        public void bind(Page page, int position) {


            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }

            configurePhotoView();

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_RGB_565)
                    .downsample(DownsampleStrategy.AT_MOST)
                    .fitCenter();

            Glide.with(context)
                    .load(page.getImageUrl())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                            applyReadingMode();
                            return false;
                        }
                    })
                    .into(imgPage);
        }

        private void configurePhotoView() {
            imgPage.setZoomable(true);
            imgPage.setMinimumScale(0.8f);
            imgPage.setMaximumScale(4.0f);
            imgPage.setMediumScale(2.0f);
            imgPage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgPage.setAdjustViewBounds(true);
        }

        private void applyReadingMode() {
            switch (readingMode) {
                case FIT_WIDTH:
                default:
                    imgPage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imgPage.setAdjustViewBounds(true);
                    break;

                case FIT_HEIGHT:
                    imgPage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;

                case FIT_SCREEN:
                    imgPage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;

                case ORIGINAL_SIZE:
                    imgPage.setScaleType(ImageView.ScaleType.CENTER);
                    imgPage.setAdjustViewBounds(false);
                    break;
            }
        }
    }
}