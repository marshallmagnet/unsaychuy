package com.whatsbest.nexusbond.wassup.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatsbest.nexusbond.wassup.Classes.Users;
import com.whatsbest.nexusbond.wassup.R;

/**
 * This java class is the first to be launch whenever the app starts
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int GOOGLE_SIGN_IN = 9001;

    private GoogleApiClient googleApiClient;
    private TextView txt_register;
    private EditText et_email, et_password;
    private Button btn_login, btn_google, btn_facebook;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthlistener;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        firebaseAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if (firebaseUser != null) {
                    toLandingActivity();
                }
            }
        };
        forGoogleOptions();

        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        txt_register = (TextView) findViewById(R.id.txt_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_google = (Button) findViewById(R.id.btn_google);
        btn_facebook = (Button) findViewById(R.id.btn_facebook);

        txt_register.setOnClickListener(onClick());
        btn_login.setOnClickListener(onClick());
        btn_google.setOnClickListener(onClick());
        btn_facebook.setOnClickListener(onClick());
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthlistener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthlistener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthlistener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Toaster("Authentication failed");
            }
        }
    }

    // after choosing the account to use to login, this method retrieves the credentials of the user
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.hasChild(user.getUid())) {
                                        storeData(user);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toaster("Authentication failed");
                            firebaseAuth.signOut();
                        }
                    }
                });
    }

    // stores the data into the realtime databse on firebase
    private void storeData(FirebaseUser mUser) {
        Users user = new Users(mUser.getDisplayName(), mUser.getEmail(), mUser.getPhotoUrl().toString(), mUser.getPhotoUrl().toString());
        databaseReference.child("Users").child(mUser.getUid()).setValue(user);
    }

    private void forGoogleOptions() {
        GoogleSignInOptions googleSignOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignOptions)
                .build();
    }

    private void loginUser() {
        firebaseAuth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toaster("Authentication failed.");
                }
            }
        });
    }

    // pops up the google fragment upon login
    private void toSignInGoogle() {
        Auth.GoogleSignInApi.signOut(googleApiClient);
        Intent googleIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(googleIntent, GOOGLE_SIGN_IN);
    }

    // goes to the landing activity if the user has login successfully
    private void toLandingActivity() {
        Intent toLandingActivity = new Intent(LoginActivity.this, LandingActivity.class);
        startActivity(toLandingActivity);
        this.finish();
    }

    // goes to the registration activity
    private void toRegisterActivity() {
        Intent toRegistryActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(toRegistryActivity);
    }

    // creates toast messages
    private void Toaster(String text) {
        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.txt_register:
                        toRegisterActivity();
                        break;
                    case R.id.btn_login:
                        loginUser();
                        break;
                    case R.id.btn_google:
                        toSignInGoogle();
                        break;
                    case R.id.btn_facebook:
                        break;
                }
            }
        };
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}