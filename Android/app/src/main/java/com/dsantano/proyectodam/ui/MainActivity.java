package com.dsantano.proyectodam.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.common.SharedPreferencesManager;
import com.dsantano.proyectodam.listeners.IAllLocationsListener;
import com.dsantano.proyectodam.listeners.IAllServicesListener;
import com.dsantano.proyectodam.listeners.IAllUsersListener;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.models.services.Services;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.ui.auth.LoginActivity;
import com.dsantano.proyectodam.ui.locations.locationdetail.LocationDetailActivity;
import com.dsantano.proyectodam.ui.services.servicedetail.ServiceDetailActivity;
import com.dsantano.proyectodam.ui.users.userdetail.UserDetailActivity;
import com.dsantano.proyectodam.ui.users.userprofile.UserProfileActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements IAllUsersListener, IAllServicesListener, IAllLocationsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void googleLogOut(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleLogin = GoogleSignIn.getClient(this, googleSignInOptions);
        FirebaseAuth.getInstance().signOut();
        mGoogleLogin.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent i =  new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                SharedPreferences settings = this.getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE);
                settings.edit().clear().apply();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.action_profile:
                Intent profileI = new Intent(this, UserProfileActivity.class);
                startActivity(profileI);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAllUsersItemClick(User user) {
        Intent i = new Intent(MainActivity.this, UserDetailActivity.class);
        i.putExtra(Constants.PUT_EXTRA_USER_ID, user.id);
        startActivity(i);
    }

    @Override
    public void onAllServicesItemClick(Services services) {
        SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_SERVICE_USER_ID, services.getUserOfferingService());
        Intent i = new Intent(MainActivity.this, ServiceDetailActivity.class);
        i.putExtra(Constants.PUT_EXTRA_SERVICE_ID, services.id);
        startActivity(i);
    }

    @Override
    public void onAllLocationsItemClick(LocationUser locationUser) {
        SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_LOCATION_USER_ID, locationUser.getId());
        Intent i = new Intent(MainActivity.this, LocationDetailActivity.class);
        i.putExtra(Constants.PUT_EXTRA_LOCATION_ID, locationUser.getLocationOffered().getId());
        startActivity(i);
    }
}
