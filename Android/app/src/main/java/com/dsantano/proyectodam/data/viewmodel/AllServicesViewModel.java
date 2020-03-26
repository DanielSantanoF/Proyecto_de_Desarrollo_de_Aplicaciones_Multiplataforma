package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.ServiceRepository;
import com.dsantano.proyectodam.models.services.Services;

import java.util.List;

public class AllServicesViewModel extends AndroidViewModel {

    private ServiceRepository serviceRepository;
    private MutableLiveData<List<Services>> allServices;

    public AllServicesViewModel(@NonNull Application application) {
        super(application);
        serviceRepository = new ServiceRepository();
    }

    public MutableLiveData<List<Services>> getAllServices(){
        allServices = serviceRepository.getallServices();
        return allServices;
    }
}
