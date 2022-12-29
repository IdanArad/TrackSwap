package com.example.trackswap;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LastFMApiService {
    @GET("2.0/")
    Call<Response> searchSongs(@Query("method") String method,
                                     @Query("track") String track,
                                     @Query("artist") String artist,
                                     @Query("api_key") String apiKey,
                                     @Query("format") String format);
}
