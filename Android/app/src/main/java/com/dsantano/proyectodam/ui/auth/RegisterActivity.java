package com.dsantano.proyectodam.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.datepicker.DateTransformation;
import com.dsantano.proyectodam.datepicker.DialogDatePickerFragment;
import com.dsantano.proyectodam.datepicker.IDatePickerListener;
import com.dsantano.proyectodam.models.auth.UserRegisterResponse;
import com.dsantano.proyectodam.retrofit.servicegenerators.LoginServiceGenerator;
import com.dsantano.proyectodam.retrofit.service.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements IDatePickerListener {

    EditText edUsername, edEmail, edPassword, edConfirmPassword, edName, edPhone;
    Button btnPickBirthDate, btnDoRegister;
    TextView txtDateSelected;
    Spinner spinnerTypeUser;
    ArrayAdapter typeUserSpinnerArrayAdapter;
    List<String> spinnerTypeUsersList = new ArrayList<>();
    ImageView ivAvatar;
    String dateSelected, typeUserSelected, username, email, password, confirmPassword, name, phone;
    Service service;
    private static final int READ_REQUEST_CODE = 42;
    Uri uriSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextUsernameRegister);
        edEmail = findViewById(R.id.editTextEmailRegister);
        edPassword = findViewById(R.id.editTextPasswordRegister);
        edConfirmPassword = findViewById(R.id.editTextConfirmPasswordRegister);
        edName = findViewById(R.id.editTextNameRegister);
        edPhone = findViewById(R.id.editTextPhoneRegister);
        txtDateSelected = findViewById(R.id.textViewBirthDateRegister);

        spinnerTypeUser = findViewById(R.id.spinnerTypeUserRegister);
        spinnerTypeUsersList.add("Buscando compañía");
        spinnerTypeUsersList.add("Ofreciendo alojamiento");
        spinnerTypeUsersList.add("Joven ofreciendo compañía");
        typeUserSpinnerArrayAdapter = new ArrayAdapter(RegisterActivity.this, R.layout.support_simple_spinner_dropdown_item, spinnerTypeUsersList);
        spinnerTypeUser.setAdapter(typeUserSpinnerArrayAdapter);

        spinnerTypeUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int type = position + 1;
                if(type == 1){
                    typeUserSelected = "BUSCA_COMPANIA";
                } else if(type == 2){
                    typeUserSelected = "OFRECE_ALOJAMIENTO";
                } else if(type == 3){
                    typeUserSelected = "JOVEN";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ivAvatar = findViewById(R.id.imageViewUploadAvatarRegister);
        Glide.with(this).load(R.drawable.ic_upload).into(ivAvatar);

        btnPickBirthDate = findViewById(R.id.buttonPickBirthDateRegister);
        btnDoRegister = findViewById(R.id.buttonDoRegister);

        btnPickBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = DialogDatePickerFragment.newInstance(RegisterActivity.this);
                datePickerFragment.show(RegisterActivity.this.getSupportFragmentManager(), "datePicker");
            }
        });

        btnDoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        int correctMonth = month + 1;
        dateSelected = year + "-" + correctMonth + "-" + day;
        DateTransformation dateTransformation = new DateTransformation();
        txtDateSelected.setText(dateTransformation.dateTransformation(dateSelected));
    }

    public void doRegister() {
        username = edUsername.getText().toString();
        email = edEmail.getText().toString();
        password = edPassword.getText().toString();
        confirmPassword = edConfirmPassword.getText().toString();
        name = edName.getText().toString();
        phone = edPhone.getText().toString();
        service = LoginServiceGenerator.createService(Service.class);
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Complete all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (uriSelected != null) {

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uriSelected);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    int cantBytes;
                    byte[] buffer = new byte[1024 * 4];

                    while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                        baos.write(buffer, 0, cantBytes);
                    }


                    RequestBody requestFile =
                            RequestBody.create(baos.toByteArray(),
                                    MediaType.parse(getContentResolver().getType(uriSelected)));


                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("avatar", "avatar", requestFile);

                    RequestBody usernameRequest = RequestBody.create(MultipartBody.FORM, username);
                    RequestBody emailRequest = RequestBody.create(MultipartBody.FORM, email);
                    RequestBody nameRequest = RequestBody.create(MultipartBody.FORM, name);
                    RequestBody passwordRequest = RequestBody.create(MultipartBody.FORM, password);
                    RequestBody confirmPasswordRequest = RequestBody.create(MultipartBody.FORM, confirmPassword);
                    RequestBody phoneRequest = RequestBody.create(MultipartBody.FORM, phone);
                    RequestBody typeUserRequest = RequestBody.create(MultipartBody.FORM, typeUserSelected);
                    RequestBody dateOfBirthRequest = RequestBody.create(MultipartBody.FORM, dateSelected);

                    Call<UserRegisterResponse> call = service.register(usernameRequest, emailRequest, nameRequest, passwordRequest, confirmPasswordRequest, phoneRequest, typeUserRequest, dateOfBirthRequest, body);
                    call.enqueue(new Callback<UserRegisterResponse>() {
                        @Override
                        public void onResponse(retrofit2.Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                            if (response.isSuccessful()) {
                                Intent i =  new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(MyApp.getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                                Log.i("apiLogin", "Entra en onResponse ero da error");
                            }
                        }

                        @Override
                        public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                            Toast.makeText(MyApp.getContext(), getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                            Log.i("apiLogin", "Fail en el login");
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        public void performFileSearch(){
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Log.i("Filechooser URI", "Uri: " + uri.toString());
                    Glide
                            .with(this)
                            .load(uri)
                            .transform(new CircleCrop())
                            .into(ivAvatar);
                    uriSelected = uri;
                }
            }
        }

}
