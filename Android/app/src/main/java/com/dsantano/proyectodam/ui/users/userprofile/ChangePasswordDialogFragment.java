package com.dsantano.proyectodam.ui.users.userprofile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.models.users.ChangePasswordSended;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialogFragment extends DialogFragment {

    View v;
    EditText etPassword, etConfirmPassword;
    Context ctx;
    Service service;
    Activity act;

    public ChangePasswordDialogFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getResources().getString(R.string.change_password_title));
        builder.setMessage(getResources().getString(R.string.change_password_body));

        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_password, null);
        builder.setView(v);

        etPassword = v.findViewById(R.id.editTextPassword);
        etConfirmPassword = v.findViewById(R.id.editTextConfirmPassword);

        builder.setPositiveButton(getResources().getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                ChangePasswordSended changePasswordSended = new ChangePasswordSended(password, confirmPassword);

                if(password.isEmpty() || confirmPassword.isEmpty()) {
                    if(password.isEmpty()) {
                        etPassword.setError(getResources().getString(R.string.password_needed));
                    }
                    if(confirmPassword.isEmpty()) {
                        etConfirmPassword.setError(getResources().getString(R.string.confirm_password_needed));
                    }
                } else {
                    act = (Activity) ctx;
                    service = TokenServiceGenerator.createService(Service.class);
                    Call<User> callPostNewTask =  service.putPassword(changePasswordSended);
                    callPostNewTask.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Toast.makeText(act, getResources().getString(R.string.correct_update), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(act, getResources().getString(R.string.error_in_update), Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

}
