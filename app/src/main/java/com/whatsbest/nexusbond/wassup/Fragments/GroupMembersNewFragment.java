package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whatsbest.nexusbond.wassup.Adapters.GroupMembersAdapter;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * Use the {@link GroupMembersNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupMembersNewFragment extends Fragment {

    private static String group_id;

    private View view;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DataSource dataSource;
    private RecyclerView recycler_view;
    private LinearLayoutManager linearLayoutManager;
    private EditText et_search;
    private Button btn_search;
    private GroupMembersAdapter groupMembersAdapter;

    public static GroupMembersNewFragment newInstance(String passed_id) {
        GroupMembersNewFragment groupMembersNewFragment = new GroupMembersNewFragment();
        group_id = passed_id;
        return groupMembersNewFragment;
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
        view = inflater.inflate(R.layout.fragment_group_members_new, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dataSource = new DataSource();

        et_search = (EditText) view.findViewById(R.id.et_search);
        btn_search = (Button) view.findViewById(R.id.btn_search);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setHasFixedSize(true);
        btn_search.setOnClickListener(onClick());

        dataSource.setUser_id(firebaseUser.getUid());
        fetchUsers();
        return view;
    }

    private void initializeAdapter() {
        groupMembersAdapter = new GroupMembersAdapter(dataSource.getGroupsList(), getContext());
        recycler_view.setAdapter(groupMembersAdapter);
    }
    private void newinitializeAdapter() {
        groupMembersAdapter = new GroupMembersAdapter(dataSource.getInvitedList(), getContext());
        recycler_view.setAdapter(groupMembersAdapter);
    }

    private void fetchUsers()
    {
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setUser_snap(dataSnapshot);
                fetchMembers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchMembers()
    {
        databaseReference.child("Groups").child(group_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setMemberUser_snap(dataSnapshot);
                initializeAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchUser()
    {
        dataSource.searchMember(et_search.getText().toString());
        newinitializeAdapter();
    }
    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.btn_search:
                        searchUser();
                        break;
                }
            }
        };
    }

}
