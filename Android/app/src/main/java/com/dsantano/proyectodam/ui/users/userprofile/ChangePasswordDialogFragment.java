package com.dsantano.proyectodam.ui.users.userprofile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.models.users.ChangePasswordSended;
import com.dsantano.proyectodam.models.users.User;
import com.dsantano.proyectodam.models.users.UserFirebase;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.retrofit.servicegenerators.TokenServiceGenerator;
import com.dsantano.proyectodam.ui.auth.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordDialogFragment extends DialogFragment {

    View v;
    EditText etPassword, etConfirmPassword;
    Context ctx;
    Service service;
    Activity act;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserFirebase user;
    Map<String, Object> userfb;
    String uid, password, confirmPassword;

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
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
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
                    Call<User> call =  service.putPassword(changePasswordSended);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("changePassword", "changed");
                            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                if (uid != null) {
                                    DocumentReference docIdRef = db.collection(Constants.FIREBASE_COLLECTION_USERS).document(uid);
                                    docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    user = document.toObject(UserFirebase.class);
                                                    if (user != null) {
                                                        userfb = new HashMap<>();
                                                        userfb.put("password", password);
                                                        db.collection(Constants.FIREBASE_COLLECTION_USERS)
                                                                .document(uid)
                                                                .update(userfb);
                                                    }
                                                    Log.d("FB", "Document exists!");
                                                } else {
                                                    Log.d("FB", "Document does not exist!");
                                                }
                                            } else {
                                                Log.d("FB", "Failed with: ", task.getException());
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("changePassword", "onFailure: " + t.getMessage());
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
