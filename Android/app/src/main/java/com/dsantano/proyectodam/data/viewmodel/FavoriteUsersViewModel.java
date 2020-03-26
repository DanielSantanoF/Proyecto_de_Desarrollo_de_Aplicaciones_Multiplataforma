package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.UserRepository;
import com.dsantano.proyectodam.models.users.FavoriteUser;

public class FavoriteUsersViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<FavoriteUser> favoriteUsers;

    public FavoriteUsersViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<FavoriteUser> getAllFavUsers(){
        favoriteUsers = userRepository.getAllFavorites();
        return favoriteUsers;
    }
}
