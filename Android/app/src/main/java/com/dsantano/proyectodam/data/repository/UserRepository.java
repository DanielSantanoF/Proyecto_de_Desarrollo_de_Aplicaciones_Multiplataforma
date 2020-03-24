package com.dsantano.proyectodam.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserDetail;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    Service service;
    TokenServiceGenerator tokenServiceGenerator;
    MutableLiveData<List<User>> allUsers;
    MutableLiveData<UserDetail> userById;

    public UserRepository() {
        service = tokenServiceGenerator.createService(Service.class);
    }

    public MutableLiveData<List<User>> getAllUsers() {
        final MutableLiveData<List<User>> data = new MutableLiveData<>();

        Call<List<User>> call = service.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("AllUserError", "onFailure: " + t.getMessage());
            }
        });
        allUsers = data;
        return data;
    }

    public MutableLiveData<UserDetail> getUserById(String id) {
        final MutableLiveData<UserDetail> data = new MutableLiveData<>();

        Call<List<UserDetail>> call = service.getUsersById(id);
        call.enqueue(new Callback<List<UserDetail>>() {
            @Override
            public void onResponse(Call<List<UserDetail>> call, Response<List<UserDetail>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().get(0));
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserDetail>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("UserById", "onFailure: " + t.getMessage());
            }
        });
        userById = data;
        return data;
    }

}
