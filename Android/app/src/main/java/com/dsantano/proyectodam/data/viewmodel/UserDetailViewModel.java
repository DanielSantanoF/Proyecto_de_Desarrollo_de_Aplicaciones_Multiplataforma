package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.UserRepository;
import com.dsantano.proyectodam.models.users.UserIdSended;
import com.dsantano.proyectodam.models.users.UserDetail;

public class UserDetailViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<UserDetail> userById;
    private String userId;

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<UserDetail> getTicketById(){
        userById = userRepository.getUserById(userId);
        return userById;
    }

    public void setUserId(String id){
        userId = id;
    }

    public void postNewFavorite(UserIdSended userIdSended){
        userRepository.postNewFavorite(userIdSended);
    }

    public void putLivingWith(UserIdSended userIdSended){
        userRepository.putLivingWith(userIdSended);
    }

    public void deleteLivingWith(UserIdSended userIdSended){
        userRepository.deleteLivingWith(userIdSended);
    }

}
