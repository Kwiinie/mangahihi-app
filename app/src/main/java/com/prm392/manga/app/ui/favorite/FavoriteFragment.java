package com.prm392.manga.app.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392.manga.app.R;
import com.prm392.manga.app.data.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.View {

    private FavoriteContract.Presenter presenter;
    private FavoriteAdapter adapter;

    private RecyclerView rvFavorites;
    private ProgressBar progressBar;
    private TextView txtError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFavorites = view.findViewById(R.id.rvFavorites);
        progressBar = view.findViewById(R.id.progressBar);
        txtError = view.findViewById(R.id.txt_error); // ⛔️ thêm TextView này trong layout nếu chưa có

        presenter = new FavoritePresenter(this);

        adapter = new FavoriteAdapter(new ArrayList<>(), getContext());
        rvFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvFavorites.setAdapter(adapter);

        presenter.loadFavorites();

        return view;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFavorites(List<Favorite> favorites) {
        rvFavorites.setVisibility(View.VISIBLE);
        txtError.setVisibility(View.GONE);
        adapter.updateData(favorites);
    }

    @Override
    public void showError(String message) {
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(message);
        rvFavorites.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }
}
