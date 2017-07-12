package com.whatsbest.nexusbond.wassup.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whatsbest.nexusbond.wassup.Classes.Users;
import com.whatsbest.nexusbond.wassup.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_email, et_password, et_confirm_password, et_full_name;
    private Button btn_sign_up;
    private String photo_url;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);
        et_confirm_password = (EditText)findViewById(R.id.et_confirm_password);
        et_full_name = (EditText)findViewById(R.id.et_fullname);
        btn_sign_up = (Button)findViewById(R.id.btn_sign_up);

        btn_sign_up.setOnClickListener(onClick());
    }

    private void checkEditText()
    {
        if (et_email.getText().toString().isEmpty())
        {
            Toaster("Email can't be empty");
            et_email.requestFocus();
        }
        else if (et_password.getText().toString().isEmpty() || et_password.getText().toString().length() < 6)
        {
            Toaster("Password must be 6 characters");
            et_password.requestFocus();
        }
        else if (!et_confirm_password.getText().toString().equals(et_password.getText().toString()))
        {
            Toaster("Password does not match");
            et_confirm_password.requestFocus();
        }
        else if (et_full_name.getText().toString().isEmpty())
        {
            Toaster("Provide a name");
            et_full_name.requestFocus();
        }
        else
        {
            signUpUser();
        }
    }

    private void signUpUser()
    {
        firebaseAuth.createUserWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful())
                        {
                            Toaster("Registration failed");
                            firebaseAuth.signOut();
                        }
                        else if (task.isSuccessful())
                        {
                            storeData(task.getResult().getUser());
                        }
                    }
                });
    }

    private void storeData(FirebaseUser mUser)
    {
        if (mUser.getPhotoUrl() == null)
        {
            photo_url = "https://firebasestorage.googleapis.com/v0/b/socialmediagame-59835.appspot.com/o/batman-for-facebook.jpg?alt=media&token=9df98cc9-0a8f-4737-bbb4-efa392d465ec";
        }
        else if (mUser.getPhotoUrl() != null)
        {
            photo_url = mUser.getPhotoUrl().toString();
        }

        Users user = new Users(et_full_name.getText().toString(), mUser.getEmail(), photo_url, photo_url);
        databaseReference.child("Users").child(mUser.getUid()).setValue(user);
        updateProfile();
    }

    private void updateProfile()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(et_full_name.getText().toString())
                .setPhotoUri(Uri.parse(photo_url))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            firebaseAuth.signOut();
                            toLoginActivity();
                        }
                        else if(!task.isSuccessful())
                        {
                            Toaster("Error!");
                        }
                    }
                });
    }

    private void toLoginActivity()
    {
        Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(toLogin);
    }

    private void Toaster(String message)
    {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener onClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int button_id = v.getId();

                if(button_id == R.id.btn_sign_up)
                {
                    checkEditText();
                }
            }
        };
    }
}
