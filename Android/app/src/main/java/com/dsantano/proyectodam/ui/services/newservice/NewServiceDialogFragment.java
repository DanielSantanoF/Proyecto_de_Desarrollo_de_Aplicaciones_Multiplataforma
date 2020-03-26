package com.dsantano.proyectodam.ui.services.newservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.models.services.NewServiceCreated;
import com.dsantano.proyectodam.models.services.ServiceDetail;
import com.dsantano.proyectodam.models.services.ServiceSended;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewServiceDialogFragment extends DialogFragment {

    View v;
    EditText edTitle, edDescription;
    Spinner spinner;
    Context ctx;
    Service service;
    Activity act;
    ArrayAdapter typeUserSpinnerArrayAdapter;
    List<String> spinnerTypeServiceList = new ArrayList<>();
    String typeSelected;
    String dialogTitle, dialogBody, titulo, descripcion, serviceId;
    boolean editando;

    public NewServiceDialogFragment(Context ctx, String title, String body, boolean editando, String titulo, String descripcion, String serviceId) {
        this.ctx = ctx;
        this.dialogTitle = title;
        this.dialogBody = body;
        this.editando = editando;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.serviceId = serviceId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(dialogTitle);
        builder.setMessage(dialogBody);

        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_service, null);
        builder.setView(v);

        edTitle = v.findViewById(R.id.editTextTitleNewService);
        edDescription = v.findViewById(R.id.editTextDescriptionNewService);
        if(editando){
            edTitle.setText(titulo);
            edDescription.setText(descripcion);
        }
        spinner = v.findViewById(R.id.spinnerNewService);
        spinnerTypeServiceList.add(getResources().getString(R.string.do_buyout));
        spinnerTypeServiceList.add(getResources().getString(R.string.transport));
        spinnerTypeServiceList.add(getResources().getString(R.string.cook));
        spinnerTypeServiceList.add(getResources().getString(R.string.repair));
        typeUserSpinnerArrayAdapter = new ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, spinnerTypeServiceList);
        spinner.setAdapter(typeUserSpinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int type = position + 1;
                if(type == 1){
                    typeSelected = "HACER_COMPRA";
                } else if(type == 2){
                    typeSelected = "TRANSPORTE";
                } else if(type == 3){
                    typeSelected = "COCINAR";
                } else if(type == 4){
                    typeSelected = "REPARACIONES";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = edTitle.getText().toString();
                String description = edDescription.getText().toString();
                ServiceSended serviceSended = new ServiceSended(typeSelected, title, description);

                if(title.isEmpty() || description.isEmpty()) {
                    if(title.isEmpty()) {
                        edTitle.setError(getResources().getString(R.string.title_needed));
                    }
                    if(description.isEmpty()) {
                        edDescription.setError(getResources().getString(R.string.description_needed));
                    }
                } else {
                    act = (Activity) ctx;
                    service = TokenServiceGenerator.createService(Service.class);
                    if(editando) {
                        Call<ServiceDetail> callPostNewTask = service.putServiceById(serviceId, serviceSended);
                        callPostNewTask.enqueue(new Callback<ServiceDetail>() {
                            @Override
                            public void onResponse(Call<ServiceDetail> call, Response<ServiceDetail> response) {
                                Toast.makeText(act, getResources().getString(R.string.correct_update), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ServiceDetail> call, Throwable t) {
                                Toast.makeText(act, getResources().getString(R.string.error_in_update), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<NewServiceCreated> callPostNewTask = service.postNewService(serviceSended);
                        callPostNewTask.enqueue(new Callback<NewServiceCreated>() {
                            @Override
                            public void onResponse(Call<NewServiceCreated> call, Response<NewServiceCreated> response) {
                                Toast.makeText(act, getResources().getString(R.string.create_correct), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<NewServiceCreated> call, Throwable t) {
                                Toast.makeText(act, getResources().getString(R.string.error_in_create), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
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

