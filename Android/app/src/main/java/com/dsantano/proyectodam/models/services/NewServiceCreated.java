package com.dsantano.proyectodam.models.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewServiceCreated {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("type_service")
    @Expose
    public String typeService;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
}
