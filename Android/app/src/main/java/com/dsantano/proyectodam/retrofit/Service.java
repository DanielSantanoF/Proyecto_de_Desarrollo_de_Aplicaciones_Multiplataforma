package com.dsantano.proyectodam.retrofit;

import com.dsantano.proyectodam.models.UserLoginResponse;
import com.dsantano.proyectodam.models.UserRegisterResponse;
import com.dsantano.proyectodam.models.UserSendedToLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Service {

    @POST("/api/login")
    Call<UserLoginResponse> postLogin(@Body UserSendedToLogin userSendedToLogin);

    @Multipart
    @POST("/api/register")
    Call<UserRegisterResponse> register(@Part("username") RequestBody username,
                                        @Part("email") RequestBody email,
                                        @Part("name") RequestBody name,
                                        @Part("password") RequestBody password,
                                        @Part("confirmPassword") RequestBody confirmPassword,
                                        @Part("phone") RequestBody phone,
                                        @Part("typeUser") RequestBody typeUser,
                                        @Part("dateOfBirth") RequestBody dateOfBirth,
                                        @Part MultipartBody.Part avatar);

}
