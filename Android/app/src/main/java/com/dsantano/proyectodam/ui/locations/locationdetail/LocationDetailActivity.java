package com.dsantano.proyectodam.ui.locations.locationdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.data.viewmodel.LocationDetailViewModel;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.ui.locations.newlocation.NewLocationDialogFragment;
import com.dsantano.proyectodam.ui.services.newservice.NewServiceDialogFragment;
import com.dsantano.proyectodam.ui.users.userdetail.UserDetailActivity;

import java.io.ByteArrayOutputStream;

public class LocationDetailActivity extends AppCompatActivity {

    String locationId, userLoggedId, userProperatyId;
    LocationDetailViewModel locationDetailViewModel;
    ImageView ivUserOffering;
    TextView txtCountry, txtCity, txtStreet, txtPostalCode;
    ImageButton btnLocation, btnUserOffering;
    LocationUser locationLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        ivUserOffering = findViewById(R.id.imageViewUserLocationDetail);
        txtCountry = findViewById(R.id.textViewCountryLocationDetail);
        txtCity = findViewById(R.id.textViewCityLocationDetail);
        txtPostalCode = findViewById(R.id.textViewPostalCodeLocationDetail);
        txtStreet = findViewById(R.id.textViewStreetLocationDetail);
        btnLocation = findViewById(R.id.imageButtonSeeLocationDetail);
        btnUserOffering = findViewById(R.id.imageButtonUserOfferingLocationDetail);

        locationId = getIntent().getExtras().get(Constants.PUT_EXTRA_LOCATION_ID).toString();
        locationDetailViewModel = new ViewModelProvider(this).get(LocationDetailViewModel.class);
        locationDetailViewModel.setLocationId(locationId);
        loadLocation();

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.es/maps/place/"+locationLoaded.getLocationOffered().getCity()));
                LocationDetailActivity.this.startActivity(mapsIntent);
            }
        });

        btnUserOffering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LocationDetailActivity.this, UserDetailActivity.class);
                i.putExtra(Constants.PUT_EXTRA_USER_ID, locationLoaded.getId());
                startActivity(i);
            }
        });
    }

    public void loadLocation(){
        locationDetailViewModel.getLocationById().observe(this, new Observer<LocationUser>() {
            @Override
            public void onChanged(LocationUser locationUser) {
                locationLoaded = locationUser;
                txtCountry.setText(locationUser.getLocationOffered().getCountry());
                txtCity.setText(locationUser.getLocationOffered().getCity());
                txtStreet.setText(locationUser.getLocationOffered().getStreet());
                txtPostalCode.setText(getResources().getString(R.string.hint_postal_code) + ": " + locationUser.getLocationOffered().getPostalCode());
                if(locationUser.getAvatar() != null){
                    //decode base64 string to image
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(locationUser.getAvatar().getData(), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    Glide
                            .with(LocationDetailActivity.this)
                            .load(decodedImage)
                            .thumbnail(Glide.with(LocationDetailActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivUserOffering);
                } else {
                    Glide
                            .with(LocationDetailActivity.this)
                            .load(getDrawable(R.drawable.default_user))
                            .thumbnail(Glide.with(LocationDetailActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivUserOffering);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        userLoggedId = getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_PERSONAL_ID, null);
        userProperatyId = getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_LOCATION_USER_ID, null);
        if(userLoggedId.equals(userProperatyId)) {
            getMenuInflater().inflate(R.menu.location_detail_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_location:
                locationDetailViewModel.deleteLocation();
                Toast.makeText(this, getResources().getString(R.string.location_deleted), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_edit_location:
                NewLocationDialogFragment dialog = new NewLocationDialogFragment(this, getResources().getString(R.string.edit_location_title), getResources().getString(R.string.edit_location_body), locationLoaded.getLocationOffered().getCountry(), locationLoaded.getLocationOffered().getIsoCode() ,locationLoaded.getLocationOffered().getCity(), locationLoaded.getLocationOffered().getStreet(), locationLoaded.getLocationOffered().getPostalCode(), locationLoaded.getLocationOffered().getId(), true);
                dialog.show(getSupportFragmentManager(), "NewLocationDialogFragment");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLocation();
    }
}
