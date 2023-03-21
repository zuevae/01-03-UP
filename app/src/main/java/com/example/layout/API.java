package com.example.layout;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    @POST("user/login")
    Call<UserMask> createUser(@Body ModelUser modelUser);
}
