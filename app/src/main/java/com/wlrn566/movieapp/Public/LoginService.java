package com.wlrn566.movieapp.Public;

import com.wlrn566.movieapp.Vo.ResultVO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("/login.php")
    Call<ResultVO> getUser(
            @Field("id") String id,
            @Field("pw") String pw
    );
}
