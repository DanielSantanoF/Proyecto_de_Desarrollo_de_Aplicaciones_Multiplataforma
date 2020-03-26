package com.dsantano.proyectodam.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditUserSended {
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
    @SerializedName("typeUser")
    @Expose
    public String typeUser;
    @SerializedName("dateOfBirth")
    @Expose
    public String dateOfBirth;
}
