package com.wlrn566.movieapp.function;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Review {
    @FormUrlEncoded
    @POST("/insert_review.php")
    Call<String> getReview(
            @Field("id") String id,
            @Field("movieNm") String movieNm,
            @Field("content") String content
    );
}
