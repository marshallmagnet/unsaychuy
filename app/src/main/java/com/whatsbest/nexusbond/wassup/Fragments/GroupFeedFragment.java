package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsbest.nexusbond.wassup.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFeedFragment extends Fragment {
    private View view;
    private static String group_id;

    public static GroupFeedFragment newInstance(String passed_id) {
        GroupFeedFragment groupFeedFragment = new GroupFeedFragment();
        group_id = passed_id;
        return groupFeedFragment;
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
        view = inflater.inflate(R.layout.fragment_group_feed, container, false);

        return view;
    }
}
