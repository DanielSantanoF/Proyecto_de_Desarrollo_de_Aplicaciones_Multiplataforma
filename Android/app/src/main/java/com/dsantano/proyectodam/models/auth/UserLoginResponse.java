package com.dsantano.proyectodam.models.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("role")
    @Expose
    public String role;
    @SerializedName("type_user")
    @Expose
    public String typeUser;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("token")
    @Expose
    public String token;
}
