package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Posts;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Posts> posts;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

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
            iv_frame1.setOnClickListener(this);
            iv_frame2.setOnClickListener(this);
            iv_frame3.setOnClickListener(this);
            iv_frame4.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("UNFOLLOW")) {
                        alertDialog("Confirm Unfollow", "Are you sure? You won't be able see this person's posts any longer.", "UNFOLLOW");
                    } else if (btn_follow.getText().toString().equals("END VOTE")) {
                        alertDialog("End Voting", "Are you sure you want to end the voting?", "END_VOTE");
                    }
                    break;
                case R.id.iv_frame1:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_1");
                    }
                    break;
                case R.id.iv_frame2:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_2");
                    }
                    break;
                case R.id.iv_frame3:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_3");
                    }
                    break;
                case R.id.iv_frame4:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_4");
                    }
                    break;
            }
        }

        private void alertDialog(String title, String message, final String tag) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag.equals("UNFOLLOW")) {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                    } else if (tag.equals("END_VOTE")) {
                        endVoting(posts.get(getAdapterPosition()).getPost_id());
                    } else if (tag.equals("VOTING_1")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_one(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_one());
                    } else if (tag.equals("VOTING_2")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_two(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_two());
                    } else if (tag.equals("VOTING_3")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_three(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_three());
                    } else if (tag.equals("VOTING_4")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_four(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_four());
                    }
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

        private void votedAlertDialog() {
            AlertDialog.Builder votedAlertDialog = new AlertDialog.Builder(context);
            votedAlertDialog.setTitle("Already Voted");
            votedAlertDialog.setMessage("You have already voted on this post.");
            votedAlertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            votedAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            votedAlertDialog.show();
        }
    }

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
            iv_frame1.setOnClickListener(this);
            iv_frame2.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("UNFOLLOW")) {
                        alertDialog("Confirm Unfollow", "Are you sure? You won't be able see this person's posts any longer.", "UNFOLLOW");
                    } else if (btn_follow.getText().toString().equals("END VOTE")) {
                        alertDialog("End Voting", "Are you sure you want to end the voting?", "END_VOTE");
                    }
                    break;
                case R.id.iv_frame1:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_1");
                    }
                    break;
                case R.id.iv_frame2:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_2");
                    }
                    break;
            }
        }

        private void alertDialog(String title, String message, final String tag) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag.equals("UNFOLLOW")) {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                    } else if (tag.equals("END_VOTE")) {
                        endVoting(posts.get(getAdapterPosition()).getPost_id());
                    } else if (tag.equals("VOTING_1")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_one(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_one());
                    } else if (tag.equals("VOTING_2")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_two(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_two());
                    }
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

        private void votedAlertDialog() {
            AlertDialog.Builder votedAlertDialog = new AlertDialog.Builder(context);
            votedAlertDialog.setTitle("Already Voted");
            votedAlertDialog.setMessage("You have already voted on this post.");
            votedAlertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            votedAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            votedAlertDialog.show();
        }
    }

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
            iv_frame1.setOnClickListener(this);
            iv_frame2.setOnClickListener(this);
            iv_frame3.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_follow:
                    if (btn_follow.getText().toString().equals("UNFOLLOW")) {
                        alertDialog("Confirm Unfollow", "Are you sure? You won't be able see this person's posts any longer.", "UNFOLLOW");
                    } else if (btn_follow.getText().toString().equals("END VOTE")) {
                        alertDialog("End Voting", "Are you sure you want to end the voting?", "END_VOTE");
                    }
                    break;
                case R.id.iv_frame1:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_1");
                    }
                    break;
                case R.id.iv_frame2:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_2");
                    }
                    break;
                case R.id.iv_frame3:
                    if (posts.get(getAdapterPosition()).isVoted() == true) {
                        votedAlertDialog();
                    } else if (posts.get(getAdapterPosition()).isVoted() != true) {
                        alertDialog("Confirm Vote", "You won't be able to change your vote. Are you sure?", "VOTING_3");
                    }
                    break;
            }
        }

        private void alertDialog(String title, String message, final String tag) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag.equals("UNFOLLOW")) {
                        unfollowUser(posts.get(getAdapterPosition()).getAuthor_id());
                    } else if (tag.equals("END_VOTE")) {
                        endVoting(posts.get(getAdapterPosition()).getPost_id());
                    } else if (tag.equals("VOTING_1")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_one(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_one());
                    } else if (tag.equals("VOTING_2")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_two(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_two());
                    } else if (tag.equals("VOTING_3")) {
                        voteImage(posts.get(getAdapterPosition()).getFrame_three(), posts.get(getAdapterPosition()).getPost_id(), ""+posts.get(getAdapterPosition()).getFrame_number_three());
                    }
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }

        private void votedAlertDialog() {
            AlertDialog.Builder votedAlertDialog = new AlertDialog.Builder(context);
            votedAlertDialog.setTitle("Already Voted");
            votedAlertDialog.setMessage("You have already voted on this post.");
            votedAlertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            votedAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            votedAlertDialog.show();
        }
    }

    public PostAdapter(List<Posts> posts, Context context) {
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
        switch (holder.getItemViewType()) {
            case 0:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((FourCrossHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((FourCrossHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((FourCrossHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((FourCrossHolder) holder).iv_frame3);
                Picasso.with(context).load(posts.get(position).getFrame_four_url()).into(((FourCrossHolder) holder).iv_frame4);

                if (posts.get(position).isVoted() == true) {
                    ((FourCrossHolder) holder).txt_frame1_votes.setText("" + posts.get(position).getFrame_one_votes());
                    ((FourCrossHolder) holder).txt_frame2_votes.setText("" + posts.get(position).getFrame_two_votes());
                    ((FourCrossHolder) holder).txt_frame3_votes.setText("" + posts.get(position).getFrame_three_votes());
                    ((FourCrossHolder) holder).txt_frame4_votes.setText("" + posts.get(position).getFrame_four_votes());
                } else if (posts.get(position).isVoted() != true) {
                    ((FourCrossHolder) holder).txt_frame1_votes.setText("?");
                    ((FourCrossHolder) holder).txt_frame2_votes.setText("?");
                    ((FourCrossHolder) holder).txt_frame3_votes.setText("?");
                    ((FourCrossHolder) holder).txt_frame4_votes.setText("?");
                }

                ((FourCrossHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((FourCrossHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((FourCrossHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((FourCrossHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((FourCrossHolder) holder).txt_frame4.setText("4. " + posts.get(position).getFrame_four_desc());
                ((FourCrossHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                if (posts.get(position).getAuthor_id().equals(firebaseUser.getUid())) {
                    ((FourCrossHolder) holder).btn_follow.setText("END VOTE");
                } else {
                    ((FourCrossHolder) holder).btn_follow.setText("UNFOLLOW");
                }
                break;
            case 1:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((TwoViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((TwoViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((TwoViewHolder) holder).iv_frame2);

                if (posts.get(position).isVoted() == true) {
                    ((TwoViewHolder) holder).txt_frame1_votes.setText("" + posts.get(position).getFrame_one_votes());
                    ((TwoViewHolder) holder).txt_frame2_votes.setText("" + posts.get(position).getFrame_two_votes());
                } else if (posts.get(position).isVoted() == false) {
                    ((TwoViewHolder) holder).txt_frame1_votes.setText("?");
                    ((TwoViewHolder) holder).txt_frame2_votes.setText("?");
                }

                ((TwoViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((TwoViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((TwoViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((TwoViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                if (posts.get(position).getAuthor_id().equals(firebaseUser.getUid())) {
                    ((TwoViewHolder) holder).btn_follow.setText("END VOTE");
                } else {
                    ((TwoViewHolder) holder).btn_follow.setText("UNFOLLOW");
                }
                break;
            case 2:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((TwoViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((TwoViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((TwoViewHolder) holder).iv_frame2);

                if (posts.get(position).isVoted() == true) {
                    ((TwoViewHolder) holder).txt_frame1_votes.setText("" + posts.get(position).getFrame_one_votes());
                    ((TwoViewHolder) holder).txt_frame2_votes.setText("" + posts.get(position).getFrame_two_votes());
                } else if (posts.get(position).isVoted() == false) {
                    ((TwoViewHolder) holder).txt_frame1_votes.setText("?");
                    ((TwoViewHolder) holder).txt_frame2_votes.setText("?");
                }

                ((TwoViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((TwoViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((TwoViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((TwoViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                if (posts.get(position).getAuthor_id().equals(firebaseUser.getUid())) {
                    ((TwoViewHolder) holder).btn_follow.setText("END VOTE");
                } else {
                    ((TwoViewHolder) holder).btn_follow.setText("UNFOLLOW");
                }
                break;
            case 3:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((ThreeViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((ThreeViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((ThreeViewHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((ThreeViewHolder) holder).iv_frame3);

                if (posts.get(position).isVoted() == true) {
                    ((ThreeViewHolder) holder).txt_frame1_votes.setText("" + posts.get(position).getFrame_one_votes());
                    ((ThreeViewHolder) holder).txt_frame2_votes.setText("" + posts.get(position).getFrame_two_votes());
                    ((ThreeViewHolder) holder).txt_frame3_votes.setText("" + posts.get(position).getFrame_three_votes());
                } else if (posts.get(position).isVoted() != true) {
                    ((ThreeViewHolder) holder).txt_frame1_votes.setText("?");
                    ((ThreeViewHolder) holder).txt_frame2_votes.setText("?");
                    ((ThreeViewHolder) holder).txt_frame3_votes.setText("?");
                }

                ((ThreeViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((ThreeViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((ThreeViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((ThreeViewHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((ThreeViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                if (posts.get(position).getAuthor_id().equals(firebaseUser.getUid())) {
                    ((ThreeViewHolder) holder).btn_follow.setText("END VOTE");
                } else {
                    ((ThreeViewHolder) holder).btn_follow.setText("UNFOLLOW");
                }
                break;
            case 4:
                Picasso.with(context).load(posts.get(position).getAuthor_photo_url()).transform(new CircleTransform()).into(((ThreeViewHolder) holder).iv_profile_picture);
                Picasso.with(context).load(posts.get(position).getFrame_one_url()).into(((ThreeViewHolder) holder).iv_frame1);
                Picasso.with(context).load(posts.get(position).getFrame_two_url()).into(((ThreeViewHolder) holder).iv_frame2);
                Picasso.with(context).load(posts.get(position).getFrame_three_url()).into(((ThreeViewHolder) holder).iv_frame3);

                if (posts.get(position).isVoted() == true) {
                    ((ThreeViewHolder) holder).txt_frame1_votes.setText("" + posts.get(position).getFrame_one_votes());
                    ((ThreeViewHolder) holder).txt_frame2_votes.setText("" + posts.get(position).getFrame_two_votes());
                    ((ThreeViewHolder) holder).txt_frame3_votes.setText("" + posts.get(position).getFrame_three_votes());
                } else if (posts.get(position).isVoted() != true) {
                    ((ThreeViewHolder) holder).txt_frame1_votes.setText("?");
                    ((ThreeViewHolder) holder).txt_frame2_votes.setText("?");
                    ((ThreeViewHolder) holder).txt_frame3_votes.setText("?");
                }

                ((ThreeViewHolder) holder).txt_full_name.setText(posts.get(position).getAuthor_name());
                ((ThreeViewHolder) holder).txt_frame1.setText("1. " + posts.get(position).getFrame_one_desc());
                ((ThreeViewHolder) holder).txt_frame2.setText("2. " + posts.get(position).getFrame_two_desc());
                ((ThreeViewHolder) holder).txt_frame3.setText("3. " + posts.get(position).getFrame_three_desc());
                ((ThreeViewHolder) holder).txt_desc.setText(posts.get(position).getPost_description());

                if (posts.get(position).getAuthor_id().equals(firebaseUser.getUid())) {
                    ((ThreeViewHolder) holder).btn_follow.setText("END VOTE");
                } else {
                    ((ThreeViewHolder) holder).btn_follow.setText("UNFOLLOW");
                }
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

    private void unfollowUser(String author_id) {
        databaseReference.child("Users").child(author_id).child("followers").child(firebaseUser.getUid()).removeValue();
        databaseReference.child("Users").child(firebaseUser.getUid()).child("following").child(author_id).removeValue();
    }

    private void endVoting(String post_id) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Posts/" + post_id + "/finished/", true);
        databaseReference.updateChildren(childUpdates);
    }

    private void voteImage(String image_id, String post_id, String frame_no) {
        Map<String, Object> voteUpdates = new HashMap<>();
        voteUpdates.put("/Vote_Post/" + image_id + "/" + firebaseUser.getUid(), true);
        voteUpdates.put("/Users/" + firebaseUser.getUid() + "/post_voted/" + post_id, frame_no);
        databaseReference.updateChildren(voteUpdates);
    }
}
