package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.LocationRepository;
import com.dsantano.proyectodam.models.Locations.LocationUser;

public class LocationDetailViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private MutableLiveData<LocationUser> locationById;
    private String locationId;

    public LocationDetailViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository();
    }

    public MutableLiveData<LocationUser> getLocationById(){
        locationById = locationRepository.getLocationById(locationId);
        return locationById;
    }

    public void deleteLocation(){
        locationRepository.deleteLocationById(locationId);
    }

    public void setLocationId(String id){
        locationId = id;
    }
}
