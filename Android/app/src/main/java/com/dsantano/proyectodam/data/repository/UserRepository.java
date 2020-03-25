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

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    Service service;
    TokenServiceGenerator tokenServiceGenerator;
    MutableLiveData<List<User>> allUsers;
    MutableLiveData<UserDetail> userById;
    MutableLiveData<User> me;

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

        Call<UserDetail> call = service.getUsersById(id);
        call.enqueue(new Callback<UserDetail>() {
            @Override
            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetail> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("UserById", "onFailure: " + t.getMessage());
            }
        });
        userById = data;
        return data;
    }

    public MutableLiveData<User> getMe() {
        final MutableLiveData<User> data = new MutableLiveData<>();

        Call<User> call = service.getMe();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("UserMe", "onFailure: " + t.getMessage());
            }
        });
        me = data;
        return data;
    }

    public MutableLiveData<User> updateAvatar(MultipartBody.Part avatar){
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = service.putAvatar(avatar);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    Log.i("putAvatar","Avatar correctly update");
                }else{
                    Log.e("putAvatar","Error on response user");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("putAvatar", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
        me = data;
        return data;
    }

    public MutableLiveData<User> deleteAvatar() {
        final MutableLiveData<User> data = new MutableLiveData<>();

        Call<User> call = service.deleteAvatar();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("UserDeleteAvatar", "onFailure: " + t.getMessage());
            }
        });
        me = data;
        return data;
    }

    public void deleteMe() {
        Call<ResponseBody> call = service.deleteMe();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Correct
                } else {
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.d("deleteMe", "onFailure: " + t.getMessage());
            }
        });
    }

}
