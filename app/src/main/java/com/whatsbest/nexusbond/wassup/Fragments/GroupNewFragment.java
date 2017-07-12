package com.whatsbest.nexusbond.wassup.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Groups;
import com.whatsbest.nexusbond.wassup.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class GroupNewFragment extends Fragment {
    private static final int GALLERY_REQUEST = 20;

    private View view;
    private ImageView iv_background_image;
    private EditText et_group_name, et_group_desc;
    private Button btn_create;
    private Switch sw_switch;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri image_uri;
    private String group_push_id;

    public static GroupNewFragment newInstance() {
        GroupNewFragment groupNewFragment = new GroupNewFragment();
        return groupNewFragment;
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
        view = inflater.inflate(R.layout.fragment_group_new, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        iv_background_image = (ImageView)view.findViewById(R.id.iv_background_image);
        et_group_name = (EditText)view.findViewById(R.id.et_group_name);
        et_group_desc = (EditText)view.findViewById(R.id.et_group_desc);
        btn_create = (Button)view.findViewById(R.id.btn_create);
        sw_switch = (Switch)view.findViewById(R.id.sw_switch);
        iv_background_image.setOnClickListener(onClick());
        btn_create.setOnClickListener(onClick());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                image_uri = data.getData();
                Picasso.with(getActivity()).load(image_uri).into(iv_background_image);
            }
        }
    }

    private void uploadPhoto()
    {
        group_push_id = databaseReference.child("Groups").push().getKey();
        StorageReference pictureReference = storageReference.child("Post_Pictures/" + group_push_id + "-" + image_uri.getLastPathSegment());
        pictureReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                creatGroup(downloadUrl);
            }
        });
    }

    private void creatGroup(String url)
    {
        Long timeStamp = System.currentTimeMillis()/1000L;
        Groups groups = new Groups();
        groups.setBackground_image_url(url);
        groups.setGroup_name(et_group_name.getText().toString());
        groups.setGroup_description(et_group_desc.getText().toString());
        groups.setPrivate_status(sw_switch.isChecked());
        groups.setTimestamp(0 - timeStamp);
        databaseReference.child("Groups").child(group_push_id).setValue(groups);
        databaseReference.child("Users_Groups").child(firebaseUser.getUid()).child("Admin_Groups").child(group_push_id).setValue(true);
        databaseReference.child("Groups").child(group_push_id).child("admin_members").child(firebaseUser.getUid()).setValue(true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        toGroupActivity();
                    }
                });
    }

    private void chooseImage()
    {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, GALLERY_REQUEST);
    }

    private void toGroupActivity()
    {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupFragment.newInstance());
        fragmentTransaction.commit();
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.btn_create:
                        uploadPhoto();
                        break;
                    case R.id.iv_background_image:
                        chooseImage();
                        break;

                }
            }
        };
    }
}
