package com.dsantano.proyectodam.retrofit.service;

import com.dsantano.proyectodam.models.auth.UserLoginResponse;
import com.dsantano.proyectodam.models.auth.UserRegisterResponse;
import com.dsantano.proyectodam.models.auth.UserSendedToLogin;
import com.dsantano.proyectodam.models.users.ChangePasswordSended;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserDetail;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @Multipart
    @PUT("/api/users/me/avatar")
    Call<User> putAvatar(@Part MultipartBody.Part avatar);

    @DELETE("/api/users/me/avatar")
    Call<User> deleteAvatar();

    @DELETE("/api/users/me")
    Call<ResponseBody> deleteMe();

    @PUT("/api/users/me/password")
    Call<User> putPassword(@Body ChangePasswordSended changePasswordSended);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<UserDetail> getUsersById(@Path("id")String id);

}
