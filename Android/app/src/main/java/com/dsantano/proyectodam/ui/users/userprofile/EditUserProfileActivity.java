package com.dsantano.proyectodam.ui.users.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.data.viewmodel.UserMeViewModel;
import com.dsantano.proyectodam.datepicker.DateTransformation;
import com.dsantano.proyectodam.datepicker.DialogDatePickerFragment;
import com.dsantano.proyectodam.datepicker.IDatePickerListener;
import com.dsantano.proyectodam.ui.auth.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class EditUserProfileActivity extends AppCompatActivity implements IDatePickerListener {

    EditText edUsername, edEmail, edName, edPhone;
    Button btnPickBirthDate, btnDoRegister;
    TextView txtDateSelected;
    Spinner spinnerTypeUser;
    ArrayAdapter typeUserSpinnerArrayAdapter;
    List<String> spinnerTypeUsersList = new ArrayList<>();
    String dateSelected, typeUserSelected, username, email, name, phone;
    UserMeViewModel userMeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        userMeViewModel = new ViewModelProvider(this).get(UserMeViewModel.class);
        name = getIntent().getExtras().get(Constants.SHARED_PREFERENCES_USER_NAME).toString();
        typeUserSelected = getIntent().getExtras().get(Constants.SHARED_PREFERENCES_USER_TYPE).toString();


        edUsername = findViewById(R.id.editTextUsernameRegister);
        edEmail = findViewById(R.id.editTextEmailRegister);
        edName = findViewById(R.id.editTextNameRegister);
        edPhone = findViewById(R.id.editTextPhoneRegister);
        txtDateSelected = findViewById(R.id.textViewBirthDateRegister);

        spinnerTypeUser = findViewById(R.id.spinnerTypeUserRegister);
        spinnerTypeUsersList.add(getResources().getString(R.string.searching_compani));
        spinnerTypeUsersList.add(getResources().getString(R.string.offer_occupation));
        spinnerTypeUsersList.add(getResources().getString(R.string.offer_compani));
        spinnerTypeUsersList.add(getResources().getString(R.string.offer_service));
        typeUserSpinnerArrayAdapter = new ArrayAdapter(EditUserProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, spinnerTypeUsersList);
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
                } else if(type == 4){
                    typeUserSelected = "OFRECE_SERVICIO";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnPickBirthDate = findViewById(R.id.buttonPickBirthDateRegister);
        btnPickBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = DialogDatePickerFragment.newInstance(EditUserProfileActivity.this);
                datePickerFragment.show(EditUserProfileActivity.this.getSupportFragmentManager(), "datePicker");
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
}
