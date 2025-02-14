package com.dsantano.proyectodam.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {
    Service service;
    TokenServiceGenerator tokenServiceGenerator;
    MutableLiveData<List<LocationUser>> allLocations;
    MutableLiveData<LocationUser> locationById;

    public LocationRepository() {
        service = tokenServiceGenerator.createService(Service.class);
    }

    public MutableLiveData<List<LocationUser>> getAllLocations() {
        final MutableLiveData<List<LocationUser>> data = new MutableLiveData<>();

        Call<List<LocationUser>> call = service.getAllLocations();
        call.enqueue(new Callback<List<LocationUser>>() {
            @Override
            public void onResponse(Call<List<LocationUser>> call, Response<List<LocationUser>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    for (LocationUser locationUser: data.getValue()) {
                        List<String> list = new ArrayList<>();
                        if(locationUser.getLocationOffered() != null) {
                            if (locationUser.getLocationOffered().getCountry() == null || locationUser.getLocationOffered().getCountry().isEmpty()) {
                                locationUser.getLocationOffered().setCountry("Undefined");
                            }
                            if (locationUser.getLocationOffered().getCity() == null || locationUser.getLocationOffered().getCity().isEmpty()) {
                                locationUser.getLocationOffered().setCity("Undefined");
                            }
                            if (locationUser.getLocationOffered().getStreet() == null || locationUser.getLocationOffered().getStreet().isEmpty()) {
                                locationUser.getLocationOffered().setStreet("Undefined");
                            }
                            list.add(locationUser.getLocationOffered().getCountry());
                            list.add(locationUser.getLocationOffered().getCity());
                            list.add(locationUser.getLocationOffered().getStreet());
                            locationUser.setPalabrasClave(list);
                        }
                    }
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocationUser>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("allLocationsError", "onFailure: " + t.getMessage());
            }
        });
        allLocations = data;
        return data;
    }

    public MutableLiveData<LocationUser> getLocationById(String id) {
        final MutableLiveData<LocationUser> data = new MutableLiveData<>();

        Call<LocationUser> call = service.getLocationById(id);
        call.enqueue(new Callback<LocationUser>() {
            @Override
            public void onResponse(Call<LocationUser> call, Response<LocationUser> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationUser> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("LocationById", "onFailure: " + t.getMessage());
            }
        });
        locationById = data;
        return data;
    }

    public void deleteLocationById(String id){
        Call<ResponseBody> call = service.deleteLocationById(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.i("deleteLocationById","Correctly deleted");
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("deleteLocationById", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
