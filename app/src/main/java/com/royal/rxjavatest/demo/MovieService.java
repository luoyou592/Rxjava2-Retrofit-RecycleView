package com.royal.rxjavatest.demo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 25505 on 2017/7/23.
 */

public interface MovieService {
    @GET("top250")
    Observable<ResponseInfo> getMovieList(@Query("start") int start, @Query("count") int count);

}
