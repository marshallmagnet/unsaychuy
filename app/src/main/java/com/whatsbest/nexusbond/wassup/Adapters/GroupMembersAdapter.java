package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Groups;
import com.whatsbest.nexusbond.wassup.Fragments.GroupFragment;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */


public class GroupMembersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Groups> groupsList;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public class MemberHolder extends RecyclerView.ViewHolder {
        ImageView iv_picture;
        TextView txt_name;

        public MemberHolder(View itemView) {
            super(itemView);

            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
        }
    }

    public class AdminMemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_picture;
        TextView txt_name;
        Button btn_promote, btn_kick;

        public AdminMemberHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            btn_promote = (Button) itemView.findViewById(R.id.btn_promote);
            btn_kick = (Button) itemView.findViewById(R.id.btn_kick);
            btn_promote.setOnClickListener(this);
            btn_kick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_promote:
                    alertDialog("Promote Member", "Do you want to make " + groupsList.get(getAdapterPosition()).getMember_name() + " into a group admin?", "PROMOTE");
                    break;
                case R.id.btn_kick:
                    alertDialog("Kick Member", "Are you sure you want to kick " + groupsList.get(getAdapterPosition()).getMember_name(), "KICK");
                    break;
            }
        }

        private void alertDialog(String title, String message, final String tag) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_person_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag.equals("PROMOTE")) {
                        promoteMember(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
                    } else if (tag.equals("KICK")) {
                        kickMember(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
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
    }

    public class PendingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton btn_check, btn_reject;
        ImageView iv_picture;
        TextView txt_name;

        public PendingHolder(View itemView) {
            super(itemView);

            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            btn_check = (ImageButton) itemView.findViewById(R.id.btn_check);
            btn_reject = (ImageButton) itemView.findViewById(R.id.btn_reject);
            btn_check.setOnClickListener(this);
            btn_reject.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_check:
                    acceptRequest(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
                    break;
                case R.id.btn_reject:
                    rejectRequest(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
                    break;
            }
        }
    }

    public class OwnerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_leave;
        ImageView iv_picture;
        TextView txt_name;

        public OwnerHolder(View itemView) {
            super(itemView);

            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            btn_leave = (Button) itemView.findViewById(R.id.btn_leave);
            btn_leave.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_leave:
                    alertDialog("Leave Group", "Do you really want to leave the group?");
                    break;
            }
        }

        private void alertDialog(String title, String message) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_person_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (groupsList.get(getAdapterPosition()).isAdmin_status() == true) {
                        leaveGroup(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id(), "ADMIN");
                    } else if (groupsList.get(getAdapterPosition()).isAdmin_status() == false) {
                        leaveGroup(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id(), "MEMBER");
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
    }

    public class InviteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_picture;
        TextView txt_name;
        Button btn_invite;

        public InviteHolder(View itemView) {
            super(itemView);
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            btn_invite = (Button) itemView.findViewById(R.id.btn_invite);
            btn_invite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_invite:
                    if (btn_invite.getText().toString().equals("Cancel Invite")) {
                        alertDialog("Cancel Invitation", "Do you want to cancel invitation?", "CANCEL");
                    } else if (btn_invite.getText().toString().equals("Invite")) {
                        alertDialog("Invite", "Invite " + groupsList.get(getAdapterPosition()).getMember_name() + " to the group?", "INVITE");
                    }
                    break;
            }
        }

        private void alertDialog(String title, String message, final String tag) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.ic_person_black_24dp);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag.equals("CANCEL")) {
                        cancelInvite(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
                    } else if (tag.equals("INVITE")) {
                        inviteMember(groupsList.get(getAdapterPosition()).getMember_id(), groupsList.get(getAdapterPosition()).getGroup_id());
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
    }

    public GroupMembersAdapter(List<Groups> groupsList, Context context) {
        this.groupsList = groupsList;
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
                View admin_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_member_holder, parent, false);
                viewHolder = new MemberHolder(admin_holder);
                break;
            case 1:
                View member_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_member_holder, parent, false);
                viewHolder = new MemberHolder(member_holder);
                break;
            case 2:
                View admin_member_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_admin_member_holder, parent, false);
                viewHolder = new AdminMemberHolder(admin_member_holder);
                break;
            case 3:
                View member_pending_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_member_holder, parent, false);
                viewHolder = new MemberHolder(member_pending_holder);
                break;
            case 4:
                View pending_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_admin_pending_holder, parent, false);
                viewHolder = new PendingHolder(pending_holder);
                break;
            case 5:
                View own_holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_group_member_owner_holder, parent, false);
                viewHolder = new OwnerHolder(own_holder);
                break;
            case 6:
                View invite_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_group_member_invite, parent, false);
                viewHolder = new InviteHolder(invite_holder);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((MemberHolder) holder).iv_picture);
                ((MemberHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 1:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((MemberHolder) holder).iv_picture);
                ((MemberHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 2:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((AdminMemberHolder) holder).iv_picture);
                ((AdminMemberHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 3:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((MemberHolder) holder).iv_picture);
                ((MemberHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 4:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((PendingHolder) holder).iv_picture);
                ((PendingHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 5:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((OwnerHolder) holder).iv_picture);
                ((OwnerHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());
                break;
            case 6:
                Picasso.with(context).load(groupsList.get(position).getMember_photo_url()).transform(new CircleTransform()).into(((InviteHolder) holder).iv_picture);
                ((InviteHolder) holder).txt_name.setText(groupsList.get(position).getMember_name());

                if (groupsList.get(position).isPending_invite() == true) {
                    ((InviteHolder) holder).btn_invite.setText("Cancel Invite");
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int int_value;

        Log.d("See", "This 1 " + (groupsList.get(position).isAdmin_status() == true && !groupsList.get(position).getMember_id().equals(firebaseUser.getUid()))
        + " This 2 " + (groupsList.get(position).isMember_status() == true && groupsList.get(position).isAdmin_viewer() == false && !groupsList.get(position).getMember_id().equals(firebaseUser.getUid()))
        + " This 3 " + (groupsList.get(position).isMember_status() == true && groupsList.get(position).isAdmin_viewer() == true)
        + " This 4 " + (groupsList.get(position).isPending_status() == true && groupsList.get(position).isAdmin_viewer() == false)
        + " This 5 " + (groupsList.get(position).isPending_status() == true && groupsList.get(position).isAdmin_viewer() == true)
        + " This 6 " + ((groupsList.get(position).isAdmin_status() == true && groupsList.get(position).getMember_id().equals(firebaseUser.getUid())) || (groupsList.get(position).isMember_status() == true && groupsList.get(position).getMember_id().equals(firebaseUser.getUid())))
        + " This 7 " + (groupsList.get(position).isInvite_status() == true && groupsList.get(position).isAdmin_viewer() == true) + " name " + groupsList.get(position).getMember_name()
        + " status " + groupsList.get(position).isInvite_status());

        if (groupsList.get(position).isAdmin_status() == true && !groupsList.get(position).getMember_id().equals(firebaseUser.getUid())) {
            int_value = 0;
        } else if (groupsList.get(position).isMember_status() == true && groupsList.get(position).isAdmin_viewer() == false && !groupsList.get(position).getMember_id().equals(firebaseUser.getUid())) {
            int_value = 1;
        } else if (groupsList.get(position).isMember_status() == true && groupsList.get(position).isAdmin_viewer() == true) {
            int_value = 2;
        } else if (groupsList.get(position).isPending_status() == true && groupsList.get(position).isAdmin_viewer() == false) {
            int_value = 3;
        } else if (groupsList.get(position).isPending_status() == true && groupsList.get(position).isAdmin_viewer() == true) {
            int_value = 4;
        } else if ((groupsList.get(position).isAdmin_status() == true && groupsList.get(position).getMember_id().equals(firebaseUser.getUid())) || (groupsList.get(position).isMember_status() == true && groupsList.get(position).getMember_id().equals(firebaseUser.getUid()))) {
            int_value = 5;
        } else if (groupsList.get(position).isInvite_status() == true && groupsList.get(position).isAdmin_viewer() == true) {
            int_value = 6;
        } else {
            int_value = -1;
        }

        return int_value;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void acceptRequest(String user_id, String group_id) {
        Map<String, Object> acceptUser = new HashMap<>();
        acceptUser.put("/Groups/" + group_id + "/members/" + user_id, true);
        acceptUser.put("/Users_Groups/" + user_id + "/Member_Groups/" + group_id, true);
        databaseReference.updateChildren(acceptUser);

        databaseReference.child("Groups").child(group_id).child("pending_members").child(user_id).removeValue();
        databaseReference.child("Users_Groups").child(user_id).child("Pending_Groups").child(group_id).removeValue();
    }

    private void rejectRequest(String user_id, String group_id) {
        databaseReference.child("Groups").child(group_id).child("pending_members").child(user_id).removeValue();
        databaseReference.child("Users_Groups").child(user_id).child("Pending_Groups").child(group_id).removeValue();
    }

    private void promoteMember(String user_id, String group_id) {
        Map<String, Object> promoteUser = new HashMap<>();
        promoteUser.put("/Groups/" + group_id + "/admin_members/" + user_id, true);
        promoteUser.put("/Users_Groups/" + user_id + "/Admin_Groups/" + group_id, true);
        databaseReference.updateChildren(promoteUser);

        databaseReference.child("Groups").child(group_id).child("members").child(user_id).removeValue();
        databaseReference.child("Users_Groups").child(user_id).child("Member_Groups").child(group_id).removeValue();
    }

    private void kickMember(String user_id, String group_id) {
        databaseReference.child("Groups").child(group_id).child("members").child(user_id).removeValue();
        databaseReference.child("Users_Groups").child(user_id).child("Member_Groups").child(group_id).removeValue();
    }

    private void leaveGroup(String user_id, String group_id, String tag) {
        if (tag.equals("ADMIN")) {
            databaseReference.child("Groups").child(group_id).child("admin_members").child(user_id).removeValue();
            databaseReference.child("Users_Groups").child(user_id).child("Admin_Groups").child(group_id).removeValue();
        } else if (tag.equals("MEMBER")) {
            databaseReference.child("Groups").child(group_id).child("members").child(user_id).removeValue();
            databaseReference.child("Users_Groups").child(user_id).child("Member_Groups").child(group_id).removeValue();
        }
        toGroupFragment();
    }

    private void inviteMember(String user_id, String group_id)
    {
        Map<String, Object> inviteMember = new HashMap<>();
        inviteMember.put("/Groups/" + group_id + "/invited_pending_members/" + user_id, true);
        inviteMember.put("/Users_Groups/" + user_id + "/Pending_Groups/" + group_id, firebaseUser.getUid());
        databaseReference.updateChildren(inviteMember);
    }

    private void cancelInvite(String user_id, String group_id)
    {
        databaseReference.child("Groups").child(group_id).child("invited_pending_members").child(user_id).removeValue();
        databaseReference.child("Users_Groups").child(user_id).child("Pending_Groups").child(group_id).removeValue();
    }

    private void toGroupFragment() {
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, GroupFragment.newInstance());
        fragmentTransaction.commit();
    }
}
