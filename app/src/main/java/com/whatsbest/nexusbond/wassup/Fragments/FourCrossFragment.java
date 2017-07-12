package com.whatsbest.nexusbond.wassup.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Activities.LandingActivity;
import com.whatsbest.nexusbond.wassup.Classes.Images;
import com.whatsbest.nexusbond.wassup.Classes.Posts;
import com.whatsbest.nexusbond.wassup.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FourCrossFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourCrossFragment extends Fragment {
    private static final int GALLERY_REQUEST = 20;
    private static final String FRAME_TYPE = "FOUR_CROSS";

    private View view;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private ImageView iv_frame1, iv_frame2, iv_frame3, iv_frame4;
    private EditText et_frame1, et_frame2, et_frame3, et_frame4, et_desc;
    private Button btn_post;
    private Uri[] image_uri;
    private String[] image_strings, image_id;
    private String post_push_id, images_push_id;
    private int frame_marker;

    public static FourCrossFragment newInstance() {
        FourCrossFragment fourCrossFragment = new FourCrossFragment();
        return fourCrossFragment;
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
        view = inflater.inflate(R.layout.fragment_frame_four_cross, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        iv_frame1 = (ImageView) view.findViewById(R.id.iv_frame1);
        iv_frame2 = (ImageView) view.findViewById(R.id.iv_frame2);
        iv_frame3 = (ImageView) view.findViewById(R.id.iv_frame3);
        iv_frame4 = (ImageView)view.findViewById(R.id.iv_frame4);
        et_frame1 = (EditText) view.findViewById(R.id.et_frame1);
        et_frame2 = (EditText) view.findViewById(R.id.et_frame2);
        et_frame3 = (EditText) view.findViewById(R.id.et_frame3);
        et_frame4 = (EditText) view.findViewById(R.id.et_frame4);
        et_desc = (EditText) view.findViewById(R.id.et_desc);
        btn_post = (Button) view.findViewById(R.id.btn_post);

        iv_frame1.setOnClickListener(onClick());
        iv_frame2.setOnClickListener(onClick());
        iv_frame3.setOnClickListener(onClick());
        iv_frame4.setOnClickListener(onClick());
        btn_post.setOnClickListener(onClick());

        image_uri = new Uri[4];
        image_strings = new String[4];
        image_id = new String[4];

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                switch (frame_marker) {
                    case 1:
                        image_uri[0] = data.getData();
                        Picasso.with(getContext()).load(image_uri[0]).into(iv_frame1);
                        break;
                    case 2:
                        image_uri[1] = data.getData();
                        Picasso.with(getContext()).load(image_uri[1]).into(iv_frame2);
                        break;
                    case 3:
                        image_uri[2] = data.getData();
                        Picasso.with(getContext()).load(image_uri[2]).into(iv_frame3);
                        break;
                    case 4:
                        image_uri[3] = data.getData();
                        Picasso.with(getContext()).load(image_uri[3]).into(iv_frame4);
                        break;
                }
            }
        }
    }

    private void uploadPictures() {
        post_push_id = databaseReference.child("Posts").push().getKey();
        int index = 0;
        for (Uri uri_index : image_uri) {
            images_push_id = databaseReference.child("Images").child(post_push_id).push().getKey();

            StorageReference pictureReference = storageReference.child("Post_Pictures/" + images_push_id + "-" + uri_index.getLastPathSegment());

            image_id[index] = images_push_id;

            pictureReference.putFile(uri_index)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                            //postImages(downloadUrl);
                            if (image_strings[0] == null) {
                                image_strings[0] = downloadUrl;
                                postImages(downloadUrl, et_frame1.getText().toString(), 1, image_id[0]);
                            } else if (image_strings[0] != null && image_strings[1] == null) {
                                image_strings[1] = downloadUrl;
                                //image_id[1] = images_push_id;
                                postImages(downloadUrl, et_frame2.getText().toString(), 2, image_id[1]);
                            } else if (image_strings[1] != null && image_strings[2] == null) {
                                image_strings[2] = downloadUrl;
                                //image_id[2] = images_push_id;
                                postImages(downloadUrl, et_frame3.getText().toString(), 3, image_id[2]);
                            } else if (image_strings[2] != null && image_strings[3] == null) {
                                image_strings[3] = downloadUrl;
                                postImages(downloadUrl, et_frame4.getText().toString(), 4, image_id[3]);
                                Log.d("Data", "Frame 4 - " + image_id[3]);

                                if (image_strings[0] != null && image_strings[1] != null && image_strings[2] != null && image_strings[3] != null) {
                                    createPost(image_id);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("Data", "Frame1-" + exception.toString());
                        }
                    });
            index++;
        }
    }

    private void createPost(String[] images_id) {

        Long timeStamp = System.currentTimeMillis()/1000L;
        Posts posts = new Posts();
        posts.setAuthor_info(firebaseUser.getDisplayName() + "," + firebaseUser.getEmail() + "," + firebaseUser.getPhotoUrl() + "," + firebaseUser.getUid());
        posts.setFrame_one(images_id[0]);
        posts.setFrame_two(images_id[1]);
        posts.setFrame_three(images_id[2]);
        posts.setFrame_four(images_id[3]);
        posts.setFrame_type(FRAME_TYPE);
        posts.setPost_description(et_desc.getText().toString());
        posts.setPrivate_status(false);
        posts.setFinished(false);
        posts.setTimestamp(0 - timeStamp);
        databaseReference.child("Posts").child(post_push_id).setValue(posts);
        databaseReference.child("Users_Posts").child(firebaseUser.getUid()).child(post_push_id).setValue(true);
    }

    private void postImages(String url, String desc, int frame, String push_id) {
        Images images = new Images(frame, desc, url);
        databaseReference.child("Images").child(post_push_id).child(push_id).setValue(images);
    }

    private void choosePicture() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, GALLERY_REQUEST);
    }

    private void toLandingActivity() {
        Intent toLanding = new Intent(getActivity(), LandingActivity.class);
        startActivity(toLanding);
        getActivity().finish();
    }


    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.iv_frame1:
                        frame_marker = 1;
                        choosePicture();
                        break;
                    case R.id.iv_frame2:
                        frame_marker = 2;
                        choosePicture();
                        break;
                    case R.id.iv_frame3:
                        frame_marker = 3;
                        choosePicture();
                        break;
                    case R.id.iv_frame4:
                        frame_marker = 4;
                        choosePicture();
                        break;
                    case R.id.btn_post:
                        //createPost();
                        uploadPictures();
                        toLandingActivity();
                        break;
                }
            }
        };
    }
}
