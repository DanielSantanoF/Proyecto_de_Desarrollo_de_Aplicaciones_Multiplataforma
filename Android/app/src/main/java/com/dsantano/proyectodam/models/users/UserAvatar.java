package com.dsantano.proyectodam.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAvatar {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("data")
    @Expose
    public String data;
    @SerializedName("contentType")
    @Expose
    public String contentType;
}
