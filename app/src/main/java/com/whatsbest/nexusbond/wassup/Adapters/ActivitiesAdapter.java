package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Posts;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter class for the activities fragment
 */

public class ActivitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Posts> posts;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    //holder class for the four frames layout post
    public class FourCrossHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvView;
        TextView txt_full_name, txt_frame1, txt_frame2, txt_frame3, txt_frame4, txt_desc, txt_frame1_votes, txt_frame2_votes, txt_frame3_votes, txt_frame4_votes;
        ImageView iv_profile_picture, iv_frame1, iv_frame2, iv_frame3, iv_frame4;
        Button btn_follow;

        public FourCrossHolder(View itemView) {
            super(itemView);
            cvView = (CardView) itemView.findViewById(R.id.cvView);
            txt_full_name = (TextView) itemView.findViewById(R.id.txt_full_name);
            txt_frame1 = (TextView) itemView.findViewById(R.id.txt_frame1);
            txt_frame2 = (TextView) itemView.findViewById(R.id.txt_frame2);
            txt_frame3 = (TextView) itemView.findViewById(R.id.txt_frame3);
            txt_frame4 = (TextView) itemView.findViewById(R.id.txt_frame4);
            txt_frame1_votes = (TextView) itemView.findViewById(R.id.txt_frame1_votes);
            txt_frame2_votes = (TextView) itemView.findViewById(R.id.txt_frame2_votes);
            txt_frame3_votes = (TextView) itemView.findViewById(R.id.txt_frame3_votes);
            txt_frame4_votes = (TextView) itemView.findViewById(R.id.txt_frame4_votes);
            txt_desc = (TextView) itemView.findViewById(R.id.txt_description);
            iv_profile_picture = (ImageView) itemView.findViewById(R.id.iv_profile_picture);
            iv_frame1 = (ImageView) itemView.findViewById(R.id.iv_frame1);
            iv_frame2 = (ImageView) itemView.findViewById(R.id.iv_frame2);
            iv_frame3 = (ImageView) itemView.findViewById(R.id.iv_frame3);
            iv_frame4 = (ImageView) itemView.findViewById(R.id.iv_frame4);
            btn_follow = (Button) itemView.findViewById(R.id.btn_follow);
            btn_follow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("FOLLOW")) {
                        followUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("UNFOLLOW");
                    }else if (btn_follow.getText().toString().equals("UNFOLLOW"))
                    {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("FOLLOW");
                    }
                    break;
                case R.id.iv_frame1:
                    break;
                case R.id.iv_frame2:
                    break;
                case R.id.iv_frame3:
                    break;
                case R.id.iv_frame4:
                    break;
            }
        }
    }

    //holder class for the two frames layout post
    public class TwoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvView;
        TextView txt_full_name, txt_frame1, txt_frame2, txt_desc, txt_frame1_votes, txt_frame2_votes;
        ImageView iv_profile_picture, iv_frame1, iv_frame2;
        Button btn_follow;

        public TwoViewHolder(View itemView) {
            super(itemView);
            cvView = (CardView) itemView.findViewById(R.id.cvView);
            txt_full_name = (TextView) itemView.findViewById(R.id.txt_full_name);
            txt_frame1 = (TextView) itemView.findViewById(R.id.txt_frame1);
            txt_frame2 = (TextView) itemView.findViewById(R.id.txt_frame2);
            txt_frame1_votes = (TextView) itemView.findViewById(R.id.txt_frame1_votes);
            txt_frame2_votes = (TextView) itemView.findViewById(R.id.txt_frame2_votes);
            txt_desc = (TextView) itemView.findViewById(R.id.txt_description);
            iv_profile_picture = (ImageView) itemView.findViewById(R.id.iv_profile_picture);
            iv_frame1 = (ImageView) itemView.findViewById(R.id.iv_frame1);
            iv_frame2 = (ImageView) itemView.findViewById(R.id.iv_frame2);
            btn_follow = (Button) itemView.findViewById(R.id.btn_follow);
            btn_follow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("FOLLOW")) {
                        followUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("UNFOLLOW");
                    }else if (btn_follow.getText().toString().equals("UNFOLLOW"))
                    {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("FOLLOW");
                    }
                    break;
                case R.id.iv_frame1:
                    break;
                case R.id.iv_frame2:
                    break;
            }
        }
    }

    //holder class for the three frames layout post
    public class ThreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvView;
        TextView txt_full_name, txt_frame1, txt_frame2, txt_frame3, txt_desc, txt_frame1_votes, txt_frame2_votes, txt_frame3_votes;
        ImageView iv_profile_picture, iv_frame1, iv_frame2, iv_frame3;
        Button btn_follow;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            cvView = (CardView) itemView.findViewById(R.id.cvView);
            txt_full_name = (TextView) itemView.findViewById(R.id.txt_full_name);
            txt_frame1 = (TextView) itemView.findViewById(R.id.txt_frame1);
            txt_frame2 = (TextView) itemView.findViewById(R.id.txt_frame2);
            txt_frame3 = (TextView) itemView.findViewById(R.id.txt_frame3);
            txt_frame1_votes = (TextView) itemView.findViewById(R.id.txt_frame1_votes);
            txt_frame2_votes = (TextView) itemView.findViewById(R.id.txt_frame2_votes);
            txt_frame3_votes = (TextView) itemView.findViewById(R.id.txt_frame3_votes);
            txt_desc = (TextView) itemView.findViewById(R.id.txt_description);
            iv_profile_picture = (ImageView) itemView.findViewById(R.id.iv_profile_picture);
            iv_frame1 = (ImageView) itemView.findViewById(R.id.iv_frame1);
            iv_frame2 = (ImageView) itemView.findViewById(R.id.iv_frame2);
            iv_frame3 = (ImageView) itemView.findViewById(R.id.iv_frame3);
            btn_follow = (Button) itemView.findViewById(R.id.btn_follow);
            btn_follow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("FOLLOW")) {
                        followUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("UNFOLLOW");
                    }else if (btn_follow.getText().toString().equals("UNFOLLOW"))
                    {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                        btn_follow.getText().toString().equals("FOLLOW");
                    }
                    break;
                case R.id.iv_frame1:
                    break;
                case R.id.iv_frame2:
                    break;
                case R.id.iv_frame3:
                    break;
            }
        }
    }

    //constructor
    public ActivitiesAdapter(List<Posts> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case 0:
                View four_cross_view = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_frame_four_cross, parent, false);
                viewHolder = new FourCrossHolder(four_cross_view);
                break;
            case 1:
                View two_vertical_view = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_frame_two_horizontal, parent, false);
                viewHolder = new TwoViewHolder(two_vertical_view);
                break;
            case 2:
                View two_horizontal_view = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_frame_two_vertical, parent, false);
                viewHolder = new TwoViewHolder(two_horizontal_view);
                break;
            case 3:
                View three_vertical_view = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_frame_three_horizontal, parent, false);
                viewHolder = new ThreeViewHolder(three_vertical_view);
                break;
            case 4:
                View three_horizontal_view = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_frame_three_vertical, parent, false);
                viewHolder = new ThreeViewHolder(three_horizontal_view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecyclerView.ViewHolder goHolder = holder;

        switch (holder.getItemViewType()) {
            case 0:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((FourCrossHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((FourCrossHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((FourCrossHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((FourCrossHolder) holder).iv_frame3);
                Picasso.with(context).load(posts.get(position).getFrame_four_url()).into(((FourCrossHolder) holder).iv_frame4);

                ((FourCrossHolder) holder).txt_frame1_votes.setText("");
                ((FourCrossHolder) holder).txt_frame2_votes.setText("");
                ((FourCrossHolder) holder).txt_frame3_votes.setText("");
                ((FourCrossHolder) holder).txt_frame4_votes.setText("");

                ((FourCrossHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((FourCrossHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((FourCrossHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((FourCrossHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((FourCrossHolder) holder).txt_frame4.setText("4. " + posts.get(position).getFrame_four_desc());
                ((FourCrossHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                databaseReference.child("Users").child(posts.get(position).getAuthor_id()).child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                            ((FourCrossHolder) goHolder).btn_follow.setText("UNFOLLOW");
                        } else {
                            ((FourCrossHolder) goHolder).btn_follow.setText("FOLLOW");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 1:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((TwoViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((TwoViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((TwoViewHolder) holder).iv_frame2);

                ((TwoViewHolder) holder).txt_frame1_votes.setText("");
                ((TwoViewHolder) holder).txt_frame2_votes.setText("");

                ((TwoViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((TwoViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((TwoViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((TwoViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                databaseReference.child("Users").child(posts.get(position).getAuthor_id()).child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                            ((TwoViewHolder) goHolder).btn_follow.setText("UNFOLLOW");
                        } else {
                            ((TwoViewHolder) goHolder).btn_follow.setText("FOLLOW");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 2:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((TwoViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((TwoViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((TwoViewHolder) holder).iv_frame2);

                ((TwoViewHolder) holder).txt_frame1_votes.setText("");
                ((TwoViewHolder) holder).txt_frame2_votes.setText("");

                ((TwoViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((TwoViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((TwoViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((TwoViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                databaseReference.child("Users").child(posts.get(position).getAuthor_id()).child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                            ((TwoViewHolder) goHolder).btn_follow.setText("UNFOLLOW");
                        } else {
                            ((TwoViewHolder) goHolder).btn_follow.setText("FOLLOW");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 3:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((ThreeViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((ThreeViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((ThreeViewHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((ThreeViewHolder) holder).iv_frame3);

                ((ThreeViewHolder) holder).txt_frame1_votes.setText("");
                ((ThreeViewHolder) holder).txt_frame2_votes.setText("");
                ((ThreeViewHolder) holder).txt_frame3_votes.setText("");

                ((ThreeViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((ThreeViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((ThreeViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((ThreeViewHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((ThreeViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                databaseReference.child("Users").child(posts.get(position).getAuthor_id()).child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                            ((ThreeViewHolder) goHolder).btn_follow.setText("UNFOLLOW");
                        } else {
                            ((ThreeViewHolder) goHolder).btn_follow.setText("FOLLOW");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 4:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((ThreeViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((ThreeViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((ThreeViewHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((ThreeViewHolder) holder).iv_frame3);

                ((ThreeViewHolder) holder).txt_frame1_votes.setText("");
                ((ThreeViewHolder) holder).txt_frame2_votes.setText("");
                ((ThreeViewHolder) holder).txt_frame3_votes.setText("");

                ((ThreeViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((ThreeViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((ThreeViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((ThreeViewHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((ThreeViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                databaseReference.child("Users").child(posts.get(position).getAuthor_id()).child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(firebaseUser.getUid())) {
                            ((ThreeViewHolder) goHolder).btn_follow.setText("UNFOLLOW");
                        } else {
                            ((ThreeViewHolder) goHolder).btn_follow.setText("FOLLOW");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    @Override
    public int getItemViewType(int position) {
        int int_value;

        switch (posts.get(position).getFrame_type()) {
            case "FOUR_CROSS":
                int_value = 0;
                break;
            case "TWO_VERTICAL":
                int_value = 1;
                break;
            case "TWO_HORIZONTAL":
                int_value = 2;
                break;
            case "THREE_VERTICAL":
                int_value = 3;
                break;
            case "THREE_HORIZONTAL":
                int_value = 4;
                break;
            default:
                int_value = -1;
                break;
        }
        return int_value;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //modifies the realtime databse for following other user
    private void followUser(String author_id) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Users/" + author_id + "/followers/" + firebaseUser.getUid(), true);
        childUpdates.put("/Users/" + firebaseUser.getUid() + "/following/" + author_id, true);
        databaseReference.updateChildren(childUpdates);
    }

    //modifies the realtime databse for unfollowing other users
    private void unfollowUser(String author_id) {
        databaseReference.child("Users").child(author_id).child("followers").child(firebaseUser.getUid()).removeValue();
        databaseReference.child("Users").child(firebaseUser.getUid()).child("following").child(author_id).removeValue();
    }
}