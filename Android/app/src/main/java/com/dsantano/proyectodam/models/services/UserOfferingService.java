package com.dsantano.proyectodam.models.services;

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
public class UserOfferingService {
    @SerializedName("validated")
    @Expose
    public Boolean validated;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("location_offered")
    @Expose
    public Object locationOffered;
    @SerializedName("favorite_users")
    @Expose
    public List<String> favoriteUsers = null;
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
    @SerializedName("register_date")
    @Expose
    public String registerDate;
    @SerializedName("avatar")
    @Expose
    public UserAvatar avatar;
    @SerializedName("__v")
    @Expose
    public Integer v;
}
