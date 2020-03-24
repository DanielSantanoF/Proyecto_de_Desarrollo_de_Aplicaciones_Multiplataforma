package com.dsantano.proyectodam.models.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationOffered {
    @SerializedName("available")
    @Expose
    public Boolean available;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("iso_code")
    @Expose
    public String isoCode;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("__v")
    @Expose
    public Integer v;
}
