package com.dsantano.proyectodam.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.data.repository.UserRepository;
import com.dsantano.proyectodam.models.users.EditUserSended;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserIdSended;

import okhttp3.MultipartBody;

public class UserMeViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> me;

    public UserMeViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<User> getMe(){
        me = userRepository.getMe();
        return me;
    }

    public MutableLiveData<User> updateAvatar(MultipartBody.Part avatar){
        me = userRepository.updateAvatar(avatar);
        return me;
    }

    public MutableLiveData<User> deleteAvatar(){
        me = userRepository.deleteAvatar();
        return me;
    }

    public MutableLiveData<User> updateMe(EditUserSended editUserSended){
        me = userRepository.updateMe(editUserSended);
        return me;
    }

    public void deleteLivingWith(String id){
        userRepository.deleteLivingWith(id);
    }

}
