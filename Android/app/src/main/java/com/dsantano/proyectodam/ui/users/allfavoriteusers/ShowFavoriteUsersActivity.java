package com.dsantano.proyectodam.ui.users.allfavoriteusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.listeners.IFavoriteUserListener;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.ui.users.userdetail.UserDetailActivity;

public class ShowFavoriteUsersActivity extends AppCompatActivity implements IFavoriteUserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite_users);
    }

    @Override
    public void onFavoriteUsersItemClick(User user) {
        Intent i = new Intent(ShowFavoriteUsersActivity.this, UserDetailActivity.class);
        i.putExtra(Constants.PUT_EXTRA_USER_ID, user.id);
        startActivity(i);
    }
}
