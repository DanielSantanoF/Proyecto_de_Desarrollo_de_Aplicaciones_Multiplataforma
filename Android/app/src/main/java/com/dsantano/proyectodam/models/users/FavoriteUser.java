package com.dsantano.proyectodam.models.users;

import com.dsantano.proyectodam.models.users.LocationOffered;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserAvatar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteUser {
    @SerializedName("validated")
    @Expose
    public Boolean validated;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("location_offered")
    @Expose
    public LocationOffered locationOffered;
    @SerializedName("favorite_users")
    @Expose
    public List<User> favoriteUsers = null;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("type_user")
    @Expose
    public String typeUser;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("date_of_birth")
    @Expose
    public String dateOfBirth;
    @SerializedName("avatar")
    @Expose
    public UserAvatar avatar;
}
