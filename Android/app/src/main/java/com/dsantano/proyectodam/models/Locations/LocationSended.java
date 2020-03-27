package com.dsantano.proyectodam.models.Locations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationSended {
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("isoCode")
    @Expose
    public String isoCode;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("postalCode")
    @Expose
    public String postalCode;
}
