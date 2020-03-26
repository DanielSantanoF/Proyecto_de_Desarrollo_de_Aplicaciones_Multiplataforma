package com.dsantano.proyectodam.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.services.ServiceDetail;
import com.dsantano.proyectodam.models.services.Services;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository {
    Service service;
    TokenServiceGenerator tokenServiceGenerator;
    MutableLiveData<List<Services>> allServices;
    MutableLiveData<ServiceDetail> servideById;

    public ServiceRepository() {
        service = tokenServiceGenerator.createService(Service.class);
    }

    public MutableLiveData<List<Services>> getallServices() {
        final MutableLiveData<List<Services>> data = new MutableLiveData<>();

        Call<List<Services>> call = service.getAllServices();
        call.enqueue(new Callback<List<Services>>() {
            @Override
            public void onResponse(Call<List<Services>> call, Response<List<Services>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    for (Services services: data.getValue()) {
                        List<String> list = new ArrayList<>();
                        if (services.getTitle() == null || services.getTitle().isEmpty()){
                            services.setTitle("Undefined");
                        }
                        if (services.getTypeService() == null || services.getTypeService().isEmpty()){
                            services.setTypeService("Undefined");
                        }
                        list.add(services.getTitle());
                        list.add(services.getTypeService());
                        services.setPalabrasClave(list);
                    }
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Services>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("AllServicesError", "onFailure: " + t.getMessage());
            }
        });
        allServices = data;
        return data;
    }

    public MutableLiveData<ServiceDetail> getServiceById(String id) {
        final MutableLiveData<ServiceDetail> data = new MutableLiveData<>();

        Call<ServiceDetail> call = service.getServiceById(id);
        call.enqueue(new Callback<ServiceDetail>() {
            @Override
            public void onResponse(Call<ServiceDetail> call, Response<ServiceDetail> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceDetail> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("ServiceById", "onFailure: " + t.getMessage());
            }
        });
        servideById = data;
        return data;
    }

    public void deleteServiceById(String id){
        Call<ResponseBody> call = service.deleteServiceById(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.i("deleteServiceById","Correctly deleted");
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("deleteServiceById", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
