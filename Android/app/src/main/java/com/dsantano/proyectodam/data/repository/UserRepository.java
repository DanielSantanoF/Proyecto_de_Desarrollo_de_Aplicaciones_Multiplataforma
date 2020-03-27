package com.dsantano.proyectodam.data.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.users.EditUserSended;
import com.dsantano.proyectodam.models.users.UserFirebase;
import com.dsantano.proyectodam.models.users.UserIdSended;
import com.dsantano.proyectodam.models.users.FavoriteUser;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserDetail;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    Service service;
    TokenServiceGenerator tokenServiceGenerator;
    MutableLiveData<List<User>> allUsers;
    MutableLiveData<UserDetail> userById;
    MutableLiveData<User> me;
    MutableLiveData<FavoriteUser> myFavoriteUsers;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserFirebase user;
    Map<String, Object> userfb;
    String uid;

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
                    for (User user: data.getValue()) {
                        List<String> list = new ArrayList<>();
                        if (user.getName() == null || user.getName().isEmpty()){
                            user.setName("Undefined");
                        }
                        if (user.getEmail() == null || user.getEmail().isEmpty()){
                            user.setEmail("Undefined");
                        }
                        if (user.getUsername() == null || user.getUsername().isEmpty()){
                            user.setEmail("Undefined");
                        }
                        list.add(user.getName());
                        list.add(user.getEmail());
                        list.add(user.getUsername());
                        user.setPalabrasClave(list);
                    }
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

    public MutableLiveData<User> updateMe(final EditUserSended editUserSended){
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = service.putMe(editUserSended);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    Log.i("putMe","Me correctly update");
                    if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if (uid != null) {
                            DocumentReference docIdRef = db.collection(Constants.FIREBASE_COLLECTION_USERS).document(uid);
                            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            user = document.toObject(UserFirebase.class);
                                            if (user != null) {
                                                userfb = new HashMap<>();
                                                userfb.put("username", editUserSended.getUsername());
                                                db.collection(Constants.FIREBASE_COLLECTION_USERS)
                                                        .document(uid)
                                                        .update(userfb);
                                            }
                                            Log.d("FB", "Document exists!");
                                        } else {
                                            Log.d("FB", "Document does not exist!");
                                        }
                                    } else {
                                        Log.d("FB", "Failed with: ", task.getException());
                                    }
                                }
                            });
                        }
                    }
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("putMe", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
        me = data;
        return data;
    }

    public void postNewFavorite(UserIdSended userIdSended){
        Call<FavoriteUser> call = service.postNewFavorite(userIdSended);
        call.enqueue(new Callback<FavoriteUser>() {
            @Override
            public void onResponse(Call<FavoriteUser> call, Response<FavoriteUser> response) {
                if (response.isSuccessful()){
                    Log.i("postNewFavotire","Favorite correctly added");
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavoriteUser> call, Throwable t) {
                Log.d("postNewFavotire", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void putLivingWith(UserIdSended userIdSended){
        Call<User> call = service.putLivingWith(userIdSended);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.i("putLivingWith","LivingWith correctly updated");
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("putLivingWith", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteLivingWith(String id){
        Call<User> call = service.deleteLivingWith(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.i("deleteLivingWith","LivingWith correctly deleted");
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("deleteLivingWith", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<FavoriteUser> getAllFavorites(){
        final MutableLiveData<FavoriteUser> data = new MutableLiveData<>();
        Call<FavoriteUser> call = service.getAllFavorites();
        call.enqueue(new Callback<FavoriteUser>() {
            @Override
            public void onResponse(Call<FavoriteUser> call, Response<FavoriteUser> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    Log.i("getAllFavorites","Avatar correctly update");
                    if(data.getValue().getFavoriteUsers() != null){
                        for (User user: data.getValue().getFavoriteUsers()) {
                            List<String> list = new ArrayList<>();
                            if (user.getName() == null || user.getName().isEmpty()){
                                user.setName("Undefined");
                            }
                            if (user.getEmail() == null || user.getEmail().isEmpty()){
                                user.setEmail("Undefined");
                            }
                            if (user.getUsername() == null || user.getUsername().isEmpty()){
                                user.setEmail("Undefined");
                            }
                            list.add(user.getName());
                            list.add(user.getEmail());
                            list.add(user.getUsername());
                            user.setPalabrasClave(list);
                        }
                    }
                }else{
                    Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavoriteUser> call, Throwable t) {
                Log.d("getAllFavorites", "onFailure: " + t.getMessage());
                Toast.makeText(MyApp.getContext(), MyApp.getContext().getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
            }
        });
        myFavoriteUsers = data;
        return data;
    }


}
