package com.prm392.manga.app.data.api;

import com.prm392.manga.app.data.model.Comic;
import com.prm392.manga.app.data.model.Genre;
import com.prm392.manga.app.data.model.Chapter;
import com.prm392.manga.app.data.model.Favorite;
import com.prm392.manga.app.data.model.History;
import com.prm392.manga.app.data.model.User;
import com.prm392.manga.app.data.model.TokenResponse;
import com.prm392.manga.app.data.model.CreateHistoryRequest;
import com.prm392.manga.app.data.model.AddFavoriteRequest;
import com.prm392.manga.app.data.model.LoginRequest;
import com.prm392.manga.app.data.model.RegisterRequest;
import com.prm392.manga.app.data.model.ODataResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://10.0.2.2:5069/";
    String ODATA_BASE = "odata/";
    String API_BASE = "api/";

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getAllComics(
            @Query("$filter") String filter,
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip,
            @Query("$expand") String expand
    );

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getAllComics();


    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getFeaturedComics(
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$filter") String filter
    );

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getLatestComics(
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$filter") String filter
    );

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getComicsByType(
            @Query("$filter") String typeFilter,
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip
    );

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> getComicsByGenre(
            @Query("$filter") String genreFilter,
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip
    );

    @GET(ODATA_BASE + "comics")
    Observable<ODataResponse<Comic>> searchComics(
            @Query("$filter") String searchFilter,
            @Query("$orderby") String orderBy,
            @Query("$top") Integer top,
            @Query("$skip") Integer skip
    );


    @GET(ODATA_BASE + "comics/{id}")
    Observable<Comic> getComicById(@Path("id") int comicId);

    @GET(API_BASE + "chapters/{id}")
    Observable<Chapter> getChapterById(@Path("id") int chapterId);

    @GET(API_BASE + "genres")
    Observable<List<Genre>> getAllGenres();

    @GET(API_BASE + "favorites")
    Observable<List<Favorite>> getFavorites(@Header("Authorization") String authToken);

    @POST(API_BASE + "favorites")
    Observable<Void> addToFavorites(
            @Header("Authorization") String authToken,
            @Body AddFavoriteRequest request
    );

    @DELETE(API_BASE + "favorites/{comicId}")
    Observable<Void> removeFromFavorites(
            @Header("Authorization") String authToken,
            @Path("comicId") int comicId
    );

    @GET(API_BASE + "histories")
    Observable<List<History>> getHistory(@Header("Authorization") String authToken);

    @POST(API_BASE + "histories")
    Observable<Void> createHistory(
            @Header("Authorization") String authToken,
            @Body CreateHistoryRequest request
    );

    @POST(API_BASE + "users/register")
    Observable<User> register(@Body RegisterRequest request);

    @POST(API_BASE + "users/login")
    Observable<TokenResponse> login(@Body LoginRequest request);

    @GET(API_BASE + "users/me")
    Observable<User> getCurrentUser(@Header("Authorization") String authToken);
}
