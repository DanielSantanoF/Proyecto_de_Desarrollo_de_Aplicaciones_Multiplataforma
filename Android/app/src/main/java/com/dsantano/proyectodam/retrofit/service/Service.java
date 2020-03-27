package com.dsantano.proyectodam.retrofit.service;

import com.dsantano.proyectodam.models.Locations.LocationSended;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.models.auth.UserLoginResponse;
import com.dsantano.proyectodam.models.auth.UserRegisterResponse;
import com.dsantano.proyectodam.models.auth.UserSendedToLogin;
import com.dsantano.proyectodam.models.services.NewServiceCreated;
import com.dsantano.proyectodam.models.services.ServiceDetail;
import com.dsantano.proyectodam.models.services.ServiceSended;
import com.dsantano.proyectodam.models.services.Services;
import com.dsantano.proyectodam.models.users.ChangePasswordSended;
import com.dsantano.proyectodam.models.users.EditUserSended;
import com.dsantano.proyectodam.models.users.UserIdSended;
import com.dsantano.proyectodam.models.users.FavoriteUser;
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
    @Multipart
    @POST("/api/register")
    Call<UserRegisterResponse> registerWithNoAvatar(@Part("username") RequestBody username,
                                        @Part("email") RequestBody email,
                                        @Part("name") RequestBody name,
                                        @Part("password") RequestBody password,
                                        @Part("confirmPassword") RequestBody confirmPassword,
                                        @Part("phone") RequestBody phone,
                                        @Part("typeUser") RequestBody typeUser,
                                        @Part("dateOfBirth") RequestBody dateOfBirth);

    @GET("/api/users/me")
    Call<User> getMe();

    @Multipart
    @PUT("/api/users/me/avatar")
    Call<User> putAvatar(@Part MultipartBody.Part avatar);

    @DELETE("/api/users/me/avatar")
    Call<User> deleteAvatar();

    @DELETE("/api/users/me")
    Call<ResponseBody> deleteMe();

    @PUT("/api/users/me")
    Call<User> putMe(@Body EditUserSended editUserSended);

    @PUT("/api/users/me/password")
    Call<User> putPassword(@Body ChangePasswordSended changePasswordSended);

    @GET("/api/users")
    Call<List<User>> getAllUsers();

    @GET("/api/users/{id}")
    Call<UserDetail> getUsersById(@Path("id")String id);

    @POST("/api/users/me/favorites")
    Call<FavoriteUser> postNewFavorite(@Body UserIdSended userIdSended);

    @GET("/api/users/me/favorites")
    Call<FavoriteUser> getAllFavorites();

    @DELETE("/api/users/me/favorites/{id}")
    Call<User> deleteFavoriteById(@Path("id")String id);

    @PUT("/api/users/me/livingWith")
    Call<User> putLivingWith(@Body UserIdSended userIdSended);

    @DELETE("/api/users/me/livingWit/{id}")
    Call<User> deleteLivingWith(@Path("id")String id);

    @GET("/api/services")
    Call<List<Services>> getAllServices();

    @POST("/api/services")
    Call<NewServiceCreated> postNewService(@Body ServiceSended serviceSended);

    @GET("/api/services/{id}")
    Call<ServiceDetail> getServiceById(@Path("id")String id);

    @DELETE("/api/services/{id}")
    Call<ResponseBody> deleteServiceById(@Path("id")String id);

    @PUT("/api/services/{id}")
    Call<ServiceDetail> putServiceById(@Path("id")String id, @Body ServiceSended serviceSended);

    @GET("/api/locations")
    Call<List<LocationUser>> getAllLocations();

    @GET("/api/locations/{id}")
    Call<LocationUser> getLocationById(@Path("id")String id);

    @POST("/api/locations")
    Call<LocationUser> postNewLocation(@Body LocationSended locationSended);

    @PUT("/api/locations/{id}")
    Call<LocationUser> putLocationById(@Path("id")String id, @Body LocationSended locationSended);

    @DELETE("/api/locations/{id}")
    Call<ResponseBody> deleteLocationById(@Path("id")String id);

}
