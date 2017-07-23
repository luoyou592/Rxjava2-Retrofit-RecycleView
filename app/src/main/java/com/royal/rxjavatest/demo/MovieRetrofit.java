package com.royal.rxjavatest.demo;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 25505 on 2017/7/23.
 */

public class MovieRetrofit {
    private static MovieRetrofit sMovieRetrofit;


    private final MovieService mMovieService;

    public MovieRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mMovieService = retrofit.create(MovieService.class);

    }

    public MovieService getMovieService() {
        return mMovieService;
    }

    public static MovieRetrofit getInstance(){
        if (sMovieRetrofit==null){
            synchronized (MovieRetrofit.class) {
                if (sMovieRetrofit == null) {
                    sMovieRetrofit = new MovieRetrofit();
                }
            }
        }
        return sMovieRetrofit;
    }
}
