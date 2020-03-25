package com.dsantano.proyectodam.ui.users.userprofile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;
import com.dsantano.proyectodam.ui.auth.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmDeleteMeDialogFragment extends DialogFragment {

    View v;
    Context ctx;
    Service service;
    Activity act;

    public ConfirmDeleteMeDialogFragment(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getResources().getString(R.string.delete_me_title));
        builder.setMessage(getResources().getString(R.string.delete_me_body));

        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_confirm_delete_me, null);
        builder.setView(v);

        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                act = (Activity) ctx;
                service = TokenServiceGenerator.createService(Service.class);
                Call<ResponseBody> callPostNewTask =  service.deleteMe();
                callPostNewTask.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent i = new Intent(act, LoginActivity.class);
                        act.startActivity(i);
                        act.finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(act, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
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

