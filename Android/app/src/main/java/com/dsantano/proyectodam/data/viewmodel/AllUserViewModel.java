package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.UserRepository;
import com.dsantano.proyectodam.models.users.User;

import java.util.List;

public class AllUserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<List<User>> allUsers;

    public AllUserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<List<User>> getAllUsers(){
        allUsers = userRepository.getAllUsers();
        return allUsers;
    }
}
