package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.whatsbest.nexusbond.wassup.Adapters.ActivitiesAdapter;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton btn_new_post;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private LinearLayoutManager linearLayoutManager;
    private ActivitiesAdapter activitiesAdapter;
    private DataSource dataSource;

    public static ActivitiesFragment newInstance() {
        ActivitiesFragment activitiesFragment = new ActivitiesFragment();
        return activitiesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activities, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dataSource = new DataSource();


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        btn_new_post = (FloatingActionButton) view.findViewById(R.id.fab_new_post);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        dataSource.setUser_id(firebaseUser.getUid());
        fetchPosts();
        btn_new_post.setOnClickListener(onClick());
        return view;
    }

    private void initializeAdapter() {
        activitiesAdapter = new ActivitiesAdapter(dataSource.getPosts_data(), getActivity());
        recyclerView.setAdapter(activitiesAdapter);
    }

    public void fetchPosts() {
        databaseReference.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot poSnap, String s) {
                dataSource.setActivity_snap(poSnap);
                fetchImages(poSnap.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dataSource.setActivity_snap(dataSnapshot);
                fetchImages(dataSnapshot.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot poSnap) {
                dataSource.setActivity_snap(poSnap);
                fetchImages(poSnap.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchImages(String key) {
        databaseReference.child("Images").child(key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setImage_snap(dataSnapshot);
                initializeAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dataSource.setImage_snap(dataSnapshot);
                initializeAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSource.setImage_snap(dataSnapshot);
                initializeAdapter();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void toFrameSelectionFragment() {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, FrameSelectionFragment.newInstance());
        fragmentTransaction.addToBackStack("new_post").commit();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.fab_new_post:
                        toFrameSelectionFragment();
                        break;
                }
            }
        };
    }
}