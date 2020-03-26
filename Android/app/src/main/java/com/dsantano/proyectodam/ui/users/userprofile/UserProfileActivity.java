package com.dsantano.proyectodam.ui.users.userprofile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.data.viewmodel.UserMeViewModel;
import com.dsantano.proyectodam.datepicker.DateTransformation;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserIdSended;
import com.dsantano.proyectodam.ui.users.allfavoriteusers.ShowFavoriteUsersActivity;

import org.joda.time.LocalDate;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserProfileActivity extends AppCompatActivity {

    User userLoaded;
    UserMeViewModel userMeViewModel;
    ImageView ivAvatar;
    TextView txtName, txtUsername, txtBirthDate, txtAge, txtTypeUser;
    DateTransformation dateTransformation = new DateTransformation();
    Button btnEditProfile;
    ImageButton btnChangeAvatar, btnCancelChange;
    private static final int READ_REQUEST_CODE = 42;
    private Uri uriS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ivAvatar = findViewById(R.id.imageViewAvatarUserDetail);
        txtName = findViewById(R.id.textViewNameUserDetail);
        txtUsername = findViewById(R.id.textViewUsernameUserDetail);
        txtBirthDate = findViewById(R.id.textViewBirthDateUserDetail);
        txtAge = findViewById(R.id.textViewAgeUserDetail);
        txtTypeUser = findViewById(R.id.textViewTypeUserDetail);
        btnEditProfile = findViewById(R.id.buttonEditUserProfile);
        btnChangeAvatar = findViewById(R.id.imageButtonUploadChangeAvatar);
        btnCancelChange = findViewById(R.id.imageButtonCancelChangeUserProfile);

        userMeViewModel = new ViewModelProvider(this).get(UserMeViewModel.class);
        loadUser();

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfileActivity.this, EditUserProfileActivity.class);
                i.putExtra(Constants.SHARED_PREFERENCES_USER_NAME, userLoaded.getName());
                i.putExtra(Constants.SHARED_PREFERENCES_USER_TYPE, userLoaded.getTypeUser());
                i.putExtra(Constants.SHARED_PREFERENCES_USER_USERNAME, userLoaded.getUsername());
                i.putExtra(Constants.SHARED_PREFERENCES_USER_EMAIL, userLoaded.getEmail());
                i.putExtra(Constants.SHARED_PREFERENCES_USER_PHONE, userLoaded.getPhone());
                i.putExtra(Constants.SHARED_PREFERENCES_USER_BIRTH_DATE, userLoaded.getDateOfBirth().split("T")[0]);
                startActivity(i);
            }
        });

        btnChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriS != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uriS);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024*4];

                        while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                            baos.write(buffer,0,cantBytes);
                        }

                        RequestBody requestFile =
                                RequestBody.create(baos.toByteArray(),
                                        MediaType.parse(getContentResolver().getType(uriS)));

                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("avatar", "avatar", requestFile);

                        userMeViewModel.updateAvatar(body);
                        Toast.makeText(UserProfileActivity.this, getResources().getString(R.string.avatar_update), Toast.LENGTH_SHORT).show();
                        btnChangeAvatar.setVisibility(View.GONE);
                        btnCancelChange.setVisibility(View.GONE);
                        loadUser();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(UserProfileActivity.this, getResources().getString(R.string.photo_needed), Toast.LENGTH_SHORT).show();
                    btnChangeAvatar.setVisibility(View.GONE);
                    btnCancelChange.setVisibility(View.GONE);
                }
            }
        });

        btnCancelChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriS = null;
                if(userLoaded.getAvatar() != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(userLoaded.getAvatar().getData(), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    Glide
                            .with(UserProfileActivity.this)
                            .load(decodedImage)
                            .thumbnail(Glide.with(UserProfileActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivAvatar);
                } else {
                    Glide
                            .with(UserProfileActivity.this)
                            .load(getDrawable(R.drawable.default_user))
                            .thumbnail(Glide.with(UserProfileActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivAvatar);
                }
                btnChangeAvatar.setVisibility(View.GONE);
                btnCancelChange.setVisibility(View.GONE);
            }
        });
    }

    public void loadUser(){
        userMeViewModel.getMe().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userLoaded = user;
                txtName.setText(user.getName());
                txtUsername.setText(user.getUsername());
                txtBirthDate.setText(getResources().getString(R.string.birth_date) + " " + dateTransformation.dateTransformation(user.getDateOfBirth().split("T")[0]));
                LocalDate today = new LocalDate();
                int actualYear = today.getYear();
                int actualDay = today.getDayOfMonth();
                int actualMonth = today.getMonthOfYear();
                String birthDate = user.getDateOfBirth().split("T")[0];
                int birthYear = Integer.parseInt(birthDate.split("-")[0]);
                int birthDay = Integer.parseInt(birthDate.split("-")[2]);
                int birthMonth = Integer.parseInt(birthDate.split("-")[1]);
                int age = actualYear - birthYear;
                age = age -1;
                if(actualMonth >= birthMonth){
                    if(actualDay < birthDay){
                        age = age + 1;
                    }
                }
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

                if(user.getAvatar() != null){
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] imageBytes = baos.toByteArray();
                    imageBytes = Base64.decode(user.getAvatar().getData(), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    Glide
                            .with(UserProfileActivity.this)
                            .load(decodedImage)
                            .thumbnail(Glide.with(UserProfileActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivAvatar);
                } else {
                    Glide
                            .with(UserProfileActivity.this)
                            .load(getDrawable(R.drawable.default_user))
                            .thumbnail(Glide.with(UserProfileActivity.this).load(R.drawable.loading_gif))
                            .transform(new CircleCrop())
                            .into(ivAvatar);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_password:
                ChangePasswordDialogFragment dialog = new ChangePasswordDialogFragment(UserProfileActivity.this);
                dialog.show(getSupportFragmentManager(), "ChangePasswordDialogFragment");
                break;
            case R.id.action_delete_avatar:
                userMeViewModel.deleteAvatar();
                Toast.makeText(this, getResources().getString(R.string.avatar_deleted), Toast.LENGTH_SHORT).show();
                loadUser();
                break;
            case R.id.action_delete_me:
                ConfirmDeleteMeDialogFragment dialogConfirmDelete = new ConfirmDeleteMeDialogFragment(UserProfileActivity.this);
                dialogConfirmDelete.show(getSupportFragmentManager(), "ConfirmDeleteMeDialogFragment");
                break;
            case R.id.action_go_favorites:
                Intent i = new Intent(UserProfileActivity.this, ShowFavoriteUsersActivity.class);
                startActivity(i);
                break;
            case R.id.action_delete_living_with_profile:
                if(userLoaded.getLivingWith() != null){
                    UserIdSended userIdSended = new UserIdSended(userLoaded.getLivingWith());
                    userMeViewModel.deleteLivingWith(userIdSended);
                    Toast.makeText(this, getResources().getString(R.string.living_with_deleted), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.actually_not_living_with), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void performFileSearch() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                Glide
                        .with(this)
                        .load(uri)
                        .thumbnail(Glide.with(this).load(R.drawable.loading_gif))
                        .transform(new CircleCrop())
                        .into(ivAvatar);
                uriS = uri;
                btnChangeAvatar.setVisibility(View.VISIBLE);
                btnCancelChange.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUser();
    }
}
