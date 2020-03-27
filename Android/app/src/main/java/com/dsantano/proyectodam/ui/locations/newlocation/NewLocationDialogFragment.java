package com.dsantano.proyectodam.ui.locations.newlocation;

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
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.Locations.LocationSended;
import com.dsantano.proyectodam.models.Locations.LocationUser;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLocationDialogFragment extends DialogFragment {

    View v;
    EditText edCountry, edIsoCode, edCity, edStreet, edPostalCode;
    Context ctx;
    Service service;
    Activity act;
    String dialogTitle, dialogBody, pais, isoCode, ciudad, calle, codPostal, locationId;
    boolean editando;

    public NewLocationDialogFragment(Context ctx, String dialogTitle, String dialogBody, String pais, String isoCode, String ciudad, String calle, String codPOstal, String locationId, boolean editando) {
        this.ctx = ctx;
        this.dialogTitle = dialogTitle;
        this.dialogBody = dialogBody;
        this.pais = pais;
        this.isoCode = isoCode;
        this.ciudad = ciudad;
        this.calle = calle;
        this.codPostal = codPOstal;
        this.locationId = locationId;
        this.editando = editando;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(dialogTitle);
        builder.setMessage(dialogBody);

        builder.setCancelable(true);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_location, null);
        builder.setView(v);

        edCountry = v.findViewById(R.id.editTextCountryNewLocation);
        edIsoCode = v.findViewById(R.id.editTextIsoCodeNewLocation);
        edCity = v.findViewById(R.id.editTextCityNewLocation);
        edStreet = v.findViewById(R.id.editTextStreetNewLocation);
        edPostalCode = v.findViewById(R.id.editTextPostalCodeNewLocation);

        if (editando) {
            edCountry.setText(pais);
            edIsoCode.setText(isoCode);
            edCity.setText(ciudad);
            edStreet.setText(calle);
            edPostalCode.setText(codPostal);
        }

        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String country = edCountry.getText().toString();
                String codeIso = edIsoCode.getText().toString();
                String city = edCity.getText().toString();
                String street = edStreet.getText().toString();
                String postalCode = edPostalCode.getText().toString();
                LocationSended locationSended = new LocationSended(country, codeIso, city, street, postalCode);

                if (country.isEmpty() || codeIso.isEmpty() || city.isEmpty() ||street.isEmpty() ||postalCode.isEmpty()) {
                    if (country.isEmpty()) {
                        edCountry.setError(getResources().getString(R.string.country_needed));
                    }
                    if (codeIso.isEmpty()) {
                        edIsoCode.setError(getResources().getString(R.string.iso_code_needed));
                    }
                    if (city.isEmpty()) {
                        edCity.setError(getResources().getString(R.string.city_needed));
                    }
                    if (street.isEmpty()) {
                        edStreet.setError(getResources().getString(R.string.street_needed));
                    }
                    if (postalCode.isEmpty()) {
                        edPostalCode.setError(getResources().getString(R.string.postal_code_needed));
                    }
                } else {
                    act = (Activity) ctx;
                    service = TokenServiceGenerator.createService(Service.class);
                    if (editando) {
                        Call<LocationUser> call = service.putLocationById(locationId, locationSended);
                        call.enqueue(new Callback<LocationUser>() {
                            @Override
                            public void onResponse(Call<LocationUser> call, Response<LocationUser> response) {
                                Toast.makeText(MyApp.getContext(), getResources().getString(R.string.correct_update), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<LocationUser> call, Throwable t) {
                                Toast.makeText(MyApp.getContext(), getResources().getString(R.string.error_in_update), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<LocationUser> call2 = service.postNewLocation(locationSended);
                        call2.enqueue(new Callback<LocationUser>() {
                            @Override
                            public void onResponse(Call<LocationUser> call, Response<LocationUser> response) {
                                Toast.makeText(MyApp.getContext(), getResources().getString(R.string.create_correct), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<LocationUser> call, Throwable t) {
                                Toast.makeText(MyApp.getContext(), getResources().getString(R.string.error_in_create), Toast.LENGTH_SHORT).show();
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
