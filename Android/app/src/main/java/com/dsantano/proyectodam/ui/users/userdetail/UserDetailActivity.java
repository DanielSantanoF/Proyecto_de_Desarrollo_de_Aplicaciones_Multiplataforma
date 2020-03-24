package com.dsantano.proyectodam.ui.users.userdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.data.viewmodel.UserDetailViewModel;
import com.dsantano.proyectodam.datepicker.DateTransformation;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserDetail;

import org.joda.time.LocalDate;


public class UserDetailActivity extends AppCompatActivity {

    String userId;
    UserDetail userLoaded;
    UserDetailViewModel userDetailViewModel;
    ImageView ivAvtar;
    TextView txtName, txtUsername, txtBirthDate, txtAge, txtTypeUser;
    ImageButton btnEmail, btnPhone;
    DateTransformation dateTransformation = new DateTransformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        ivAvtar = findViewById(R.id.imageViewAvatarUserDetail);
        txtName = findViewById(R.id.textViewNameUserDetail);
        txtUsername = findViewById(R.id.textViewUsernameUserDetail);
        txtBirthDate = findViewById(R.id.textViewBirthDateUserDetail);
        txtAge = findViewById(R.id.textViewAgeUserDetail);
        txtTypeUser = findViewById(R.id.textViewTypeUserDetail);
        btnEmail = findViewById(R.id.imageButtonEmailUserDetails);
        btnPhone = findViewById(R.id.imageButtonPhoneUserDetail);

        userId = getIntent().getExtras().get(Constants.SHARED_PREFERENCES_USER_ID).toString();
        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);
        userDetailViewModel.setUserId(userId);
        loadUser();

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{userLoaded.getEmail()});
                email.putExtra(Intent.EXTRA_SUBJECT, "AgesTogether");
                email.putExtra(Intent.EXTRA_TEXT, "Message: ");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:" + userLoaded.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
    }

    public void loadUser(){
        userDetailViewModel.getTicketById().observe(this, new Observer<UserDetail>() {
            @Override
            public void onChanged(UserDetail user) {
                userLoaded = user;
                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                txtBirthDate.setText(getResources().getString(R.string.birth_date) + " " + dateTransformation.dateTransformation(user.getDateOfBirth().split("T")[0]));
                LocalDate today = new LocalDate();
                int actualYear = today.getYear();
                int birthYear = Integer.parseInt(user.getDateOfBirth().split("-")[0]);
                int age = actualYear - birthYear;
                txtAge.setText(getResources().getString(R.string.age) + " " + age);
                if(user.getTypeUser().equals("BUSCA_COMPANIA")){
                    txtTypeUser.setText(getResources().getString(R.string.searching_compani));
                } else if(user.getTypeUser().equals("OFRECE_ALOJAMIENTO")){
                    txtTypeUser.setText(getResources().getString(R.string.offer_occupation));
                } else if(user.getTypeUser().equals("JOVEN")){
                    txtTypeUser.setText(getResources().getString(R.string.offer_compani));
                } else if(user.getTypeUser().equals("OFRECE_SERVICIO")) {
                    txtTypeUser.setText(getResources().getString(R.string.offer_service));
                } else {
                    txtTypeUser.setText("NaN");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_favorite:
                Toast.makeText(this, "ADD IT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
