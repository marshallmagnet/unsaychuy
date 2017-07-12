package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatsbest.nexusbond.wassup.Adapters.GroupAdapter;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {
    private View view;
    private EditText et_search;
    private Button btn_search;
    private RecyclerView recycler_mine, recycler_gen;
    private FloatingActionButton btn_new_group;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private LinearLayoutManager gen_linearLayoutManager, mine_linearLayoutManager;
    private GroupAdapter groupAdapter;
    private DataSource dataSource;

    public static GroupFragment newInstance() {
        GroupFragment groupFragment = new GroupFragment();
        return groupFragment;
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
        view = inflater.inflate(R.layout.fragment_group, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dataSource = new DataSource();

        recycler_gen = (RecyclerView) view.findViewById(R.id.recycler_gen);
        recycler_mine = (RecyclerView) view.findViewById(R.id.recycler_mine);
        btn_new_group = (FloatingActionButton) view.findViewById(R.id.fab_new_group);
        et_search = (EditText)view.findViewById(R.id.et_search);
        btn_search = (Button)view.findViewById(R.id.btn_search);
        gen_linearLayoutManager = new LinearLayoutManager(getActivity());
        gen_linearLayoutManager.setReverseLayout(true);
        gen_linearLayoutManager.setStackFromEnd(true);
        recycler_gen.setLayoutManager(gen_linearLayoutManager);
        recycler_gen.setHasFixedSize(true);
        mine_linearLayoutManager = new LinearLayoutManager(getActivity());
        mine_linearLayoutManager.setReverseLayout(true);
        mine_linearLayoutManager.setStackFromEnd(true);
        recycler_mine.setLayoutManager(mine_linearLayoutManager);
        recycler_mine.setHasFixedSize(true);
        dataSource.setUser_id(firebaseUser.getUid());
        btn_search.setOnClickListener(onClick());
        btn_new_group.setOnClickListener(onClick());

        fetchGroups();
        return view;
    }

    private void genInitializeAdapter() {
        groupAdapter = new GroupAdapter(dataSource.getGroupsList(), getActivity());
        recycler_gen.setAdapter(groupAdapter);
    }

    private void mineInitializeAdapter() {
        groupAdapter = new GroupAdapter(dataSource.getMineList(), getActivity());
        recycler_mine.setAdapter(groupAdapter);
    }

    private void fetchGroups()
    {
        databaseReference.child("Groups").orderByChild("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setGroup_snaps(dataSnapshot);
                genInitializeAdapter();
                mineInitializeAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchGroups()
    {
        dataSource.getGroupsList().clear();
        databaseReference.child("Groups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setSearch_snap(dataSnapshot, et_search.getText().toString());
                genInitializeAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    private void toGroupNewFragment()
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupNewFragment.newInstance());
        fragmentTransaction.addToBackStack("new_group").commit();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.fab_new_group:
                        toGroupNewFragment();
                        break;
                    case R.id.btn_search:
                        searchGroups();
                        break;
                }
            }
        };
    }
}

