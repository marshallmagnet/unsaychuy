package com.whatsbest.nexusbond.wassup.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.whatsbest.nexusbond.wassup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupLandingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupLandingFragment extends Fragment {
    private View view;
    private TabLayout tab_layout;
    private ViewPager view_pager;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private static String group_id;
    private String[] removeShot = new String[1];
    private String[] addedShot = new String[1];

    public static GroupLandingFragment newInstance(String passed_id) {
        GroupLandingFragment groupLandingFragment = new GroupLandingFragment();
        group_id = passed_id;
        return groupLandingFragment;
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
        view = inflater.inflate(R.layout.fragment_group_landing, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);

        GroupLandingPagerAdapter groupLandingPagerAdapter = new GroupLandingPagerAdapter(getChildFragmentManager());
        view_pager.setAdapter(groupLandingPagerAdapter);
        tab_layout.setupWithViewPager(view_pager);

        checkGroup();
        return view;
    }

    private void checkGroup() {
        databaseReference.child("Groups").child(group_id).child("admin_members").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(firebaseUser.getUid())) {
                    addedShot[0] = dataSnapshot.getKey().toString();

                    if (removeShot[0] != null) {
                        if (addedShot[0].equals(removeShot[0])) {
                            notification("Promotion", "Congratulations, you have been given administrative privileges.", "ADMIN_ADDED");
                            Log.d("Admin", "See " + dataSnapshot.getKey() + " " + addedShot[0] + " " + removeShot[0]);
                        }
                    }
                }
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


        databaseReference.child("Groups").child(group_id).child("members").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(firebaseUser.getUid())) {
                    removeShot[0] = dataSnapshot.getKey().toString();

                    if (addedShot[0] == null) {
                        Log.d("Member", "See " + dataSnapshot.getKey() + " " + addedShot[0] + " " + removeShot[0]);
                        notification("Removed", "You have been removed from the group.", "MEMBER_REMOVED");
                    }

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class GroupLandingPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"FEED", "CHAT", "MEMBERS"};

        public GroupLandingPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = GroupFeedFragment.newInstance(group_id);
                    break;
                case 1:
                    fragment = GroupChatFragment.newInstance(group_id);
                    break;
                case 2:
                    fragment = GroupMembersFragment.newInstance(group_id);
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
    }

    private void notification(String title, String message, final String tag) {
        AlertDialog.Builder notificationAlertDialog = new AlertDialog.Builder(getContext());
        notificationAlertDialog.setTitle(title);
        notificationAlertDialog.setMessage(message);
        notificationAlertDialog.setIcon(R.drawable.ic_group_black_24dp);
        notificationAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (tag.equals("MEMBER_REMOVED")) {
                    toGroupsFragment();
                } else if (tag.equals("ADMIN_ADDED")) {
                    dialog.cancel();
                }
            }
        });
        notificationAlertDialog.show();
    }

    private void toGroupsFragment() {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupFragment.newInstance());
        fragmentTransaction.commit();
    }

}
