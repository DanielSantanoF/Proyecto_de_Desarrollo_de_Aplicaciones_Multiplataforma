package com.dsantano.proyectodam.ui.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.dsantano.proyectodam.R;
import com.dsantano.proyectodam.common.Constants;
import com.dsantano.proyectodam.common.MyApp;
import com.dsantano.proyectodam.common.SharedPreferencesManager;
import com.dsantano.proyectodam.models.auth.UserLoginResponse;
import com.dsantano.proyectodam.models.auth.UserSendedToLogin;
import com.dsantano.proyectodam.retrofit.servicegenerators.LoginServiceGenerator;
import com.dsantano.proyectodam.retrofit.service.Service;
import com.dsantano.proyectodam.ui.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //Visual components
    EditText edUsername, edPassword;
    Button btnLogin, btnGoogleLogin, btnRegister;
    CardView cardView;
    ProgressBar progressBar;
    ImageView ivLogo;
    //Google Login
    private final int GOOGLE_LOGIN = 123;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleLogin;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> userfb;
    FirebaseUser firebaseUser;
    String nameOfEmail, defaultPhoto, email, photo, name;
    //Api login
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonLoginLogin);
        btnGoogleLogin = findViewById(R.id.buttonGoogleLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        cardView = findViewById(R.id.cardViewLogin);
        edUsername = findViewById(R.id.editTextUsernameLogin);
        edPassword = findViewById(R.id.editTextPasswordLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        ivLogo = findViewById(R.id.imageViewLogoLogin);

        Glide.with(this).load(R.drawable.proyectodamlogo).transform(new CircleCrop()).into(ivLogo);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleLogin = GoogleSignIn.getClient(this, googleSignInOptions);
        checkIsLogged();
        if(mAuth.getCurrentUser() != null){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()) {
                    if (username.isEmpty()) {
                        edUsername.setError(getResources().getString(R.string.username_empty));
                    }
                    if (password.isEmpty()) {
                        edPassword.setError(getResources().getString(R.string.password_empty));
                    }
                } else {
                    service = LoginServiceGenerator.createService(Service.class);
                    apiLogin(username, password);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void register(){
        Intent i =  new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void apiLogin(String username, String password){
        UserSendedToLogin userSendedToLogin = new UserSendedToLogin(username, password);
        Call<UserLoginResponse> call = service.postLogin(userSendedToLogin);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_AUTH_TOKEN,response.body().getToken());
                    SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_USER_ROLE,response.body().getRole());
                    SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_USER_TYPE,response.body().getTypeUser());
                    SharedPreferencesManager.setStringValue(Constants.SHARED_PREFERENCES_USER_PERSONAL_ID,response.body().getId());
                    Intent i =  new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(MyApp.getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("apiLogin", "Entra en onResponse pero da error");
                }
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), getResources().getString(R.string.error_in_the_connection), Toast.LENGTH_SHORT).show();
                Log.i("apiLogin", "Fail en el login");
            }
        });
    }

    public void googleLogin(){
        Intent googleLoginIntent = mGoogleLogin.getSignInIntent();
        startActivityForResult(googleLoginIntent, GOOGLE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_LOGIN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Error ", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser u) {
        checkIsLogged();
        firebaseUser = u;
        if(firebaseUser != null) {
            defaultPhoto = "https://covitalidad.edu.umh.es/wp-content/uploads/sites/1352/2018/06/default-user.png";
            userfb = new HashMap<>();
            userfb.put("uid", firebaseUser.getUid());
            userfb.put("email", firebaseUser.getEmail());
            if(firebaseUser.getDisplayName() == null || firebaseUser.getDisplayName().equals("")){
                nameOfEmail = firebaseUser.getEmail().toString().split("@")[0];
                name = nameOfEmail;
                userfb.put("name", nameOfEmail);
            } else {
                name = firebaseUser.getDisplayName();
                userfb.put("name", firebaseUser.getDisplayName());
            }
            if(firebaseUser.getPhotoUrl() == null){
                photo = defaultPhoto;
                userfb.put("photo", defaultPhoto);
            } else {
                photo = firebaseUser.getPhotoUrl().toString();
                userfb.put("photo", firebaseUser.getPhotoUrl().toString());
            }
            email = firebaseUser.getEmail();
            DocumentReference docIdRef = db.collection(Constants.FIREBASE_COLLECTION_USERS).document(firebaseUser.getUid());
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("FB", "Document exists!");
                            db.collection("users")
                                    .document(firebaseUser.getUid())
                                    .update(userfb);
                        } else {
                            Log.d("FB", "Document does not exist!");
                            db.collection("users")
                                    .document(firebaseUser.getUid())
                                    .set(userfb);
                        }
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("uid", firebaseUser.getUid());
                        startActivity(i);
                        finish();
                    } else {
                        Log.d("FB", "Failed with: ", task.getException());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Login Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkIsLogged(){
        btnLogin = findViewById(R.id.buttonLoginLogin);
        btnGoogleLogin = findViewById(R.id.buttonGoogleLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        cardView = findViewById(R.id.cardViewLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        if(FirebaseAuth.getInstance().getUid() != null){
            btnLogin.setVisibility(View.GONE);
            btnGoogleLogin.setVisibility(View.GONE);
            btnRegister.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnGoogleLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}
