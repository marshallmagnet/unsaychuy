package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.firebase.database.ValueEventListener;
import com.whatsbest.nexusbond.wassup.Adapters.PostAdapter;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;

/**
 */
public class PostFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private FloatingActionButton btn_new_post;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private LinearLayoutManager linearLayoutManager;
    private PostAdapter postAdapter;
    private DataSource dataSource;


    public static PostFragment newInstance() {
        PostFragment postFragment = new PostFragment();
        return postFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post, container, false);
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
        fetchAuthor();
        btn_new_post.setOnClickListener(onClick());
        return view;
    }

    private void initializeAdapter() {
        postAdapter = new PostAdapter(dataSource.getPosts_data(), getActivity());
        recyclerView.setAdapter(postAdapter);
    }

    private void fetchAuthor()
    {
        databaseReference.child("Users").child(firebaseUser.getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setUser_snap(dataSnapshot);
                fetchPosts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fetchPosts() {
        databaseReference.child("Posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot poSnap, String s) {
                dataSource.setPost_snap(poSnap);
                fetchImages(poSnap.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot poSnap, String s) {
                dataSource.setPost_snap(poSnap);
                Log.d("Triggerd","OnChange" + poSnap.getKey());
                fetchImages(poSnap.getKey());
            }

            @Override
            public void onChildRemoved(DataSnapshot poSnap) {
                dataSource.setPost_snap(poSnap);
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
                fetchVotes();
                initializeAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dataSource.setImage_snap(dataSnapshot);
                fetchVotes();
                initializeAdapter();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSource.setImage_snap(dataSnapshot);
                fetchVotes();
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

    private void fetchVotes()
    {
        databaseReference.child("Vote_Post").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setVote_snap(dataSnapshot);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                dataSource.setVote_snap(dataSnapshot);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

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