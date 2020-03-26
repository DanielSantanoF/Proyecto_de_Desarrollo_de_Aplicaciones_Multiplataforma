package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.ServiceRepository;
import com.dsantano.proyectodam.models.services.ServiceDetail;

public class ServiceDetailViewModel extends AndroidViewModel {

    private ServiceRepository serviceRepository;
    private MutableLiveData<ServiceDetail> serviceById;
    private String serviceId;

    public ServiceDetailViewModel(@NonNull Application application) {
        super(application);
        serviceRepository = new ServiceRepository();
    }

    public MutableLiveData<ServiceDetail> getTicketById(){
        serviceById = serviceRepository.getServiceById(serviceId);
        return serviceById;
    }

    public void deleteService(){
        serviceRepository.deleteServiceById(serviceId);
    }

    public void setServiceId(String id){
        serviceId = id;
    }
}
