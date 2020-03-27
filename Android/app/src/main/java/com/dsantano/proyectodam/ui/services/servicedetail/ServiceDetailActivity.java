package com.dsantano.proyectodam.ui.services.servicedetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.dsantano.proyectodam.data.viewmodel.ServiceDetailViewModel;
import com.dsantano.proyectodam.models.services.ServiceDetail;
import com.dsantano.proyectodam.models.users.UserIdSended;
import com.dsantano.proyectodam.ui.services.newservice.NewServiceDialogFragment;
import com.dsantano.proyectodam.ui.users.userdetail.UserDetailActivity;

public class ServiceDetailActivity extends AppCompatActivity {

    String serviceId, userLoggedId, userProperatyId;
    ImageView ivType;
    TextView txtTitle, txtType, txtDescription;
    ImageButton btnUserOffering;
    ServiceDetailViewModel serviceDetailViewModel;
    ServiceDetail serviceLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        ivType = findViewById(R.id.imageViewTypeServiceDetail);
        txtTitle = findViewById(R.id.textViewTitleServiceDetail);
        txtType = findViewById(R.id.textViewTypeServiceDetail);
        txtDescription = findViewById(R.id.textViewDescriptionServiceDetail);
        btnUserOffering = findViewById(R.id.imageButtonUserOfferingServiceDetail);

        serviceId = getIntent().getExtras().get(Constants.PUT_EXTRA_SERVICE_ID).toString();
        serviceDetailViewModel = new ViewModelProvider(this).get(ServiceDetailViewModel.class);
        serviceDetailViewModel.setServiceId(serviceId);
        loadService();

        btnUserOffering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceDetailActivity.this, UserDetailActivity.class);
                i.putExtra(Constants.PUT_EXTRA_USER_ID, serviceLoaded.getUserOfferingService().getId());
                startActivity(i);
            }
        });
    }

    public void loadService(){
        serviceDetailViewModel.getTicketById().observe(this, new Observer<ServiceDetail>() {
            @Override
            public void onChanged(ServiceDetail serviceDetail) {
                serviceLoaded = serviceDetail;
                if(serviceDetail.getTypeService().equals("HACER_COMPRA")){
                    Glide.with(ServiceDetailActivity.this).load(R.drawable.hacer_compra).transform(new CircleCrop()).into(ivType);
                    txtType.setText(getResources().getString(R.string.do_buyout));
                } else if(serviceDetail.getTypeService().equals("TRANSPORTE")){
                    Glide.with(ServiceDetailActivity.this).load(R.drawable.transport).transform(new CircleCrop()).into(ivType);
                    txtType.setText(getResources().getString(R.string.transport));
                } else if(serviceDetail.getTypeService().equals("COCINAR")){
                    Glide.with(ServiceDetailActivity.this).load(R.drawable.cooking).transform(new CircleCrop()).into(ivType);
                    txtType.setText(getResources().getString(R.string.cook));
                } else if(serviceDetail.getTypeService().equals("REPARACIONES")){
                    Glide.with(ServiceDetailActivity.this).load(R.drawable.repair).transform(new CircleCrop()).into(ivType);
                    txtType.setText(getResources().getString(R.string.repair));
                }
                txtTitle.setText(serviceDetail.getTitle());
                txtDescription.setText(serviceDetail.getDescription());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        userLoggedId = getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_USER_PERSONAL_ID, null);
        userProperatyId = getSharedPreferences(Constants.APP_SETTINGS_FILE, Context.MODE_PRIVATE).getString(Constants.SHARED_PREFERENCES_SERVICE_USER_ID, null);
        if(userLoggedId.equals(userProperatyId)) {
            getMenuInflater().inflate(R.menu.service_detail_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_service:
                serviceDetailViewModel.deleteService();
                Toast.makeText(this, getResources().getString(R.string.service_deleted), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_update_service:
                NewServiceDialogFragment dialog = new NewServiceDialogFragment(this, getResources().getString(R.string.edit_service_title), getResources().getString(R.string.edit_service_body), true, serviceLoaded.getTitle(), serviceLoaded.getDescription(), serviceLoaded.getId());
                dialog.show(getSupportFragmentManager(), "NewServiceDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadService();
    }

}
