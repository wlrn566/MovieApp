package com.wlrn566.movieapp.Service;

import com.wlrn566.movieapp.Vo.UserVO;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("/login.php")
    Call<UserVO> getUser(
            @Field("id") String id,
            @Field("pw") String pw
    );
}
