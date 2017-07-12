package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Groups;
import com.whatsbest.nexusbond.wassup.Fragments.GroupLandingFragment;
import com.whatsbest.nexusbond.wassup.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Groups> groups;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv_view;
        TextView txt_group_name, txt_group_desc;
        ImageView iv_background_image;

        public GroupHolder(View itemView) {
            super(itemView);

            cv_view = (CardView) itemView.findViewById(R.id.cv_view);
            txt_group_name = (TextView) itemView.findViewById(R.id.txt_group_name);
            txt_group_desc = (TextView) itemView.findViewById(R.id.txt_group_desc);
            iv_background_image = (ImageView) itemView.findViewById(R.id.iv_background_image);
            iv_background_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_background_image:
                    checking();
                    break;
            }
        }

        private void checking() {
            if (groups.get(getAdapterPosition()).isAdmin_status() == true) {
                toGroupFragment(groups.get(getAdapterPosition()).getGroup_id());
            } else if (groups.get(getAdapterPosition()).isMember_status() == true) {
                toGroupFragment(groups.get(getAdapterPosition()).getGroup_id());
            } else if (groups.get(getAdapterPosition()).isPending_status() == true) {
                pendingAlertDialog();
            } else if (groups.get(getAdapterPosition()).isAdmin_status() != true && groups.get(getAdapterPosition()).isMember_status() != true && groups.get(getAdapterPosition()).isPending_status() != true)
            {
                alertDialog("Request Membership", "You are not a member of this group. Do you wish to request for a membership?");
            }
        }

        private void alertDialog(String title, String message) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestMembership(groups.get(getAdapterPosition()).getGroup_id());
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

        private void pendingAlertDialog() {
            AlertDialog.Builder pendingAlertDialog = new AlertDialog.Builder(context);
            pendingAlertDialog.setTitle("Pending Request");
            pendingAlertDialog.setMessage("Your request to join the group is still not verified.");
            pendingAlertDialog.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
            pendingAlertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            pendingAlertDialog.show();
        }
    }

    public GroupAdapter(List<Groups> groups, Context context) {
        this.groups = groups;
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
        View groupHolder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_groups, parent, false);
        RecyclerView.ViewHolder viewHolder = new GroupHolder(groupHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(groups.get(position).getBackground_image_url()).into(((GroupHolder) holder).iv_background_image);
        ((GroupHolder) holder).txt_group_name.setText(groups.get(position).getGroup_name());
        ((GroupHolder) holder).txt_group_desc.setText(groups.get(position).getGroup_description());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void requestMembership(String group_id) {
        Map<String, Object> pendingMembership = new HashMap<>();
        pendingMembership.put("/Groups/" + group_id + "/pending_members/" + firebaseUser.getUid(), true);
        pendingMembership.put("/Users_Groups/" + firebaseUser.getUid() + "/Pending_Groups/" + group_id, true);
        databaseReference.updateChildren(pendingMembership);
    }

    private void toGroupFragment(String group_id)
    {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupLandingFragment.newInstance(group_id));
        fragmentTransaction.addToBackStack("group_landing").commit();
    }
}