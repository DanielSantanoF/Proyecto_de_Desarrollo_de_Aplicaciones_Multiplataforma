package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.LocationRepository;
import com.dsantano.proyectodam.models.Locations.LocationUser;

import java.util.List;

public class AllLocationsViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private MutableLiveData<List<LocationUser>> allLocations;

    public AllLocationsViewModel(@NonNull Application application) {
        super(application);
        locationRepository = new LocationRepository();
    }

    public MutableLiveData<List<LocationUser>> getAllServices(){
        allLocations = locationRepository.getAllLocations();
        return allLocations;
    }
}