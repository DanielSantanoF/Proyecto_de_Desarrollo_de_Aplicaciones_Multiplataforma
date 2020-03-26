package com.dsantano.proyectodam.models.Locations;

import com.dsantano.proyectodam.models.users.UserAvatar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationUser {
    @SerializedName("location_offered")
    @Expose
    public Location locationOffered;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("date_of_birth")
    @Expose
    public String dateOfBirth;
    @SerializedName("avatar")
    @Expose
    public UserAvatar avatar;

    public List<String> palabrasClave = new ArrayList<>();
}
