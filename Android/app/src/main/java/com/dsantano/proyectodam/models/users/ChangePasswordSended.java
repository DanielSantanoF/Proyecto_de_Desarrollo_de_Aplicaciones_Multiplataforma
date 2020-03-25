package com.dsantano.proyectodam.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordSended {
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("confirmPassword")
    @Expose
    public String confirmPassword;
}
