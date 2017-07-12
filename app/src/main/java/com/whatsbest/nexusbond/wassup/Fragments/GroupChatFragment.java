package com.whatsbest.nexusbond.wassup.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.whatsbest.nexusbond.wassup.Adapters.GroupChatAdapter;
import com.whatsbest.nexusbond.wassup.Classes.Messages;
import com.whatsbest.nexusbond.wassup.DataHandler.DataSource;
import com.whatsbest.nexusbond.wassup.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link GroupChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupChatFragment extends Fragment {
    private static final int GALLERY_REQUEST = 20;
    private static String group_id;

    private View view;
    private Uri image_uri;
    private String gm_push_id;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private LinearLayoutManager linearLayoutManager;
    private EditText et_message;
    private RecyclerView recyclerView;
    private ImageButton btn_send_pics, btn_send;
    private DataSource dataSource;
    private GroupChatAdapter groupChatAdapter;

    public static GroupChatFragment newInstance(String passed_id) {
        GroupChatFragment groupChatFragment = new GroupChatFragment();
        group_id = passed_id;
        return groupChatFragment;
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
        view = inflater.inflate(R.layout.fragment_group_chat, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        dataSource = new DataSource();

        et_message = (EditText) view.findViewById(R.id.et_message);
        btn_send_pics = (ImageButton) view.findViewById(R.id.btn_send_pic);
        btn_send = (ImageButton) view.findViewById(R.id.btn_send);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        btn_send.setOnClickListener(onClick());
        btn_send_pics.setOnClickListener(onClick());

        dataSource.setUser_id(firebaseUser.getUid());
        fetchChat();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                image_uri = data.getData();
                uploadPhoto();
            }
        }
    }

    private void fetchChat()
    {
        databaseReference.child("Group_Messages").child(group_id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSource.setMessages_snap(dataSnapshot);
                fetchSender(dataSnapshot.child("sender_id").getValue().toString());
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

    private void fetchSender(String sender_id)
    {
        databaseReference.child("Users").child(sender_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSource.setChatSender_snap(dataSnapshot);
                initializeAdapter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initializeAdapter() {
        groupChatAdapter = new GroupChatAdapter(dataSource.getMessagesList(), getActivity());
        recyclerView.setAdapter(groupChatAdapter);
    }

    private void chooseImage()
    {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker, GALLERY_REQUEST);
    }

    private void uploadPhoto()
    {
        gm_push_id = databaseReference.child("Group_Messages").child(group_id).push().getKey();
        StorageReference pictureReference = storageReference.child("Group_Chat_Pictures/" + gm_push_id + "-" + image_uri.getLastPathSegment());
        pictureReference.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                pushImage(downloadUrl);
            }
        });
    }

    private void pushImage(String url)
    {
        Messages messages =  new Messages();
        messages.setPhoto_url(url);
        messages.setSender_id(firebaseUser.getUid());
        messages.setSender_name(firebaseUser.getDisplayName());
        databaseReference.child("Group_Messages").child(group_id).child(gm_push_id).setValue(messages);
    }

    private void sendMessage()
    {
        if (et_message.getText().toString().isEmpty())
        {
            Toast.makeText(getActivity(), "Enter at least 1 word.", Toast.LENGTH_SHORT).show();
            et_message.requestFocus();
        }
        else if(!et_message.getText().toString().isEmpty())
        {
            gm_push_id = databaseReference.child("Group_Messages").child(group_id).push().getKey();
            Messages messages = new Messages();
            messages.setText(et_message.getText().toString());
            messages.setSender_id(firebaseUser.getUid());
            messages.setSender_name(firebaseUser.getDisplayName());
            databaseReference.child("Group_Messages").child(group_id).child(gm_push_id).setValue(messages);
            et_message.setText("");
        }
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int button_id = v.getId();

                switch (button_id) {
                    case R.id.btn_send:
                        sendMessage();
                        break;
                    case R.id.btn_send_pic:
                        chooseImage();
                        break;
                }
            }
        };
    }
}
