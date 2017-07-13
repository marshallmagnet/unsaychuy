package com.whatsbest.nexusbond.wassup.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Activities.LoginActivity;
import com.whatsbest.nexusbond.wassup.Adapters.ProfileAdapter;
import com.whatsbest.nexusbond.wassup.Classes.Users;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment
{
    private View view;
    private TextView txt_full_name;
    private ImageView iv_cover_photo, iv_profile_photo;
    private Button btn_sign_out;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private DataSource dataSource;
    private LinearLayoutManager linearLayoutManager;
    private ProfileAdapter profileAdapter;

    public static ProfileFragment newInstance()
    {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        txt_full_name = (TextView)view.findViewById(R.id.txt_full_name);
        iv_cover_photo = (ImageView)view.findViewById(R.id.iv_cover);
        iv_profile_photo = (ImageView)view.findViewById(R.id.iv_profile_picture);
        btn_sign_out = (Button)view.findViewById(R.id.btn_sign_out);
        recyclerView =(RecyclerView)view.findViewById(R.id.recycler_view);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        dataSource = new DataSource();

        btn_sign_out.setOnClickListener(onClick());
        dataSource.setUser_id(firebaseUser.getUid());

        fetchUser(firebaseUser);
        fetchUsersGroups();
        return view;
    }

    private void initializeData()
    {
        profileAdapter = new ProfileAdapter(dataSource.getGroupsList(), getActivity());
        recyclerView.setAdapter(profileAdapter);
    }

    private void fetchUser(FirebaseUser firebaseUser)
    {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"Please wait", "Processing");
        DatabaseReference userQuery = databaseReference.child("Users/"+ firebaseUser.getUid());

        ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Users user = dataSnapshot.getValue(Users.class);

                txt_full_name.setText(user.getDisplay_name());
                Picasso.with(getContext()).load(user.getPhoto_url()).transform(new CircleTransform()).into(iv_profile_photo);
                Picasso.with(getContext()).load(user.getCover_photo_url()).into(iv_cover_photo);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        };
        userQuery.addValueEventListener(valueEventListener);
    }

    private void fetchUsersGroups()
    {
        DatabaseReference userGroups = databaseReference.child("Users_Groups").child(firebaseUser.getUid()).child("Pending_Groups");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setInvite_Snap(dataSnapshot);
                fetchGroups();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userGroups.addValueEventListener(valueEventListener);
    }

    private void fetchGroups()
    {
        DatabaseReference groups = databaseReference.child("Groups");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setGroupDetails_snap(dataSnapshot);
                initializeData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        groups.addValueEventListener(valueEventListener);
    }

    private void logout()
    {
        firebaseAuth.signOut();
        toLoginActivity();
    }

    private void toLoginActivity()
    {
        Intent toLogin = new Intent(getActivity(), LoginActivity.class);
        startActivity(toLogin);
        getActivity().finish();
    }

    private View.OnClickListener onClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int button_id = v.getId();

                switch(button_id)
                {
                    case R.id.btn_sign_out:
                        logout();
                        break;
                }
            }
        };
    }
}

