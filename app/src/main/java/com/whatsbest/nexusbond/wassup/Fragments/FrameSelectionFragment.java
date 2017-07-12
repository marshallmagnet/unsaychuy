package com.whatsbest.nexusbond.wassup.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whatsbest.nexusbond.wassup.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FrameSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrameSelectionFragment extends Fragment {
    private View view;
    private ImageView iv_4x4_equal, iv_3x3_pool, iv_4x4_vertical;

    public static FrameSelectionFragment newInstance() {
        FrameSelectionFragment frameSelectionFragment = new FrameSelectionFragment();
        return frameSelectionFragment;
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
        view = inflater.inflate(R.layout.fragment_frame_selection, container, false);

        iv_4x4_equal = (ImageView) view.findViewById(R.id.iv_4x4_equal);
        iv_3x3_pool = (ImageView) view.findViewById(R.id.iv_3x3_pool);
        iv_4x4_vertical = (ImageView) view.findViewById(R.id.iv_4x4_vertical);

        iv_4x4_equal.setOnClickListener(onClick());
        iv_3x3_pool.setOnClickListener(onClick());
        iv_4x4_vertical.setOnClickListener(onClick());

        return view;
    }

    private void toFourCrossFragment() {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, FourCrossFragment.newInstance());
        fragmentTransaction.addToBackStack("four_cross").commit();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.iv_4x4_equal:
                        toFourCrossFragment();
                        break;
                    case R.id.iv_3x3_pool:
                        break;
                    case R.id.iv_4x4_vertical:
                        break;
                }
            }
        };
    }
}
