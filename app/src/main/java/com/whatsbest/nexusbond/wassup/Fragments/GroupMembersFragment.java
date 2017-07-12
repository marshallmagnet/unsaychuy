package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.firebase.database.ValueEventListener;
import com.whatsbest.nexusbond.wassup.Adapters.GroupMembersAdapter;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupMembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupMembersFragment extends Fragment {
    private static String group_id;

    private View view;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DataSource dataSource;
    private RecyclerView recycler_admin, recycler_members, recycler_pending;
    private LinearLayoutManager admin_linearLayoutManager, member_linearLayoutManager, pending_linearLayoutManager;
    private GroupMembersAdapter groupMembersAdapter;
    private FloatingActionButton btn_add_member;
    private ConstraintLayout constraintLayout;

    public static GroupMembersFragment newInstance(String passed_id) {
        GroupMembersFragment groupMembersFragment = new GroupMembersFragment();
        group_id = passed_id;
        return groupMembersFragment;
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
        view = inflater.inflate(R.layout.fragment_group_members, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        dataSource = new DataSource();

        constraintLayout = (ConstraintLayout)view.findViewById(R.id.cons_traint);
        btn_add_member = (FloatingActionButton)view.findViewById(R.id.fab_add_member);
        recycler_admin = (RecyclerView) view.findViewById(R.id.recycler_admin);
        recycler_members = (RecyclerView) view.findViewById(R.id.recycler_members);
        recycler_pending = (RecyclerView) view.findViewById(R.id.recycler_pending);
        admin_linearLayoutManager = new LinearLayoutManager(getActivity());
        member_linearLayoutManager = new LinearLayoutManager(getActivity());
        pending_linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_admin.setLayoutManager(admin_linearLayoutManager);
        recycler_admin.setHasFixedSize(true);
        recycler_members.setLayoutManager(member_linearLayoutManager);
        recycler_members.setHasFixedSize(true);
        recycler_pending.setLayoutManager(pending_linearLayoutManager);
        recycler_pending.setHasFixedSize(true);
        btn_add_member.setOnClickListener(onClick());

        dataSource.setUser_id(firebaseUser.getUid());
        fetchUser();
        return view;
    }

    private void compare()
    {
        for(int comp = 0; comp <dataSource.getAdminList().size(); comp++)
        {
            if(dataSource.getAdminList().get(comp).getMember_id().equals(firebaseUser.getUid()))
            {
                btn_add_member.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void initializeAdmins() {
        groupMembersAdapter = new GroupMembersAdapter(dataSource.getAdminList(), getActivity());
        recycler_admin.setAdapter(groupMembersAdapter);
    }

    private void initializeMembers() {
        groupMembersAdapter = new GroupMembersAdapter(dataSource.getMemberList(), getActivity());
        recycler_members.setAdapter(groupMembersAdapter);
    }

    private void initializePending() {
        groupMembersAdapter = new GroupMembersAdapter(dataSource.getPendingList(), getActivity());
        recycler_pending.setAdapter(groupMembersAdapter);
    }

    private void fetchUser() {
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setUser_snap(dataSnapshot);
                fetchAdmins();
                fetchMembers();
                fetchPending();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchAdmins() {
        databaseReference.child("Groups").child(group_id).child("admin_members").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setMember_admin_snap(dataSnapshot, "Added");
                compare();
                initializeAdmins();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSource.setMember_admin_snap(dataSnapshot, "Removed");
                initializeAdmins();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchMembers() {
        databaseReference.child("Groups").child(group_id).child("members").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setMember_snap(dataSnapshot, "Added");
                initializeMembers();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSource.setMember_snap(dataSnapshot, "Removed");
                initializeMembers();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetchPending() {
        databaseReference.child("Groups").child(group_id).child("pending_members").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setPending_snap(dataSnapshot, "Added");
                initializePending();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                dataSource.setPending_snap(dataSnapshot, "Removed");
                initializePending();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void toAddingMemberFragment()
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupMembersNewFragment.newInstance(group_id));
        fragmentTransaction.addToBackStack("add_member").commit();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.fab_add_member:
                        toAddingMemberFragment();
                        break;
                }
            }
        };
    }
}