package com.dsantano.proyectodam.retrofit.service;

import com.dsantano.proyectodam.models.auth.UserLoginResponse;
import com.dsantano.proyectodam.models.auth.UserRegisterResponse;
import com.dsantano.proyectodam.models.auth.UserSendedToLogin;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserDetail;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @GET("/api/users/me")
    Call<User> getMe();

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<List<UserDetail>> getUsersById(@Path("id")String id);

}
