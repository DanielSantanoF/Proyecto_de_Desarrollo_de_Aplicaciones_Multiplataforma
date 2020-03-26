package com.dsantano.proyectodam.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdSended {
    @SerializedName("idUser")
    @Expose
    public String idUser;
}
