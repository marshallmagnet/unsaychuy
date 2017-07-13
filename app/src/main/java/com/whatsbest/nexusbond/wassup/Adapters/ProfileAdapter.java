package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Groups;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

import java.util.List;

/**
 * Created by NexusNinja2 on 7/13/2017.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Groups> groups;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public class InviteHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageButton btn_check, btn_reject;
        ImageView iv_picture;
        TextView txt_name;


        public InviteHolder(View itemView) {
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
                    break;
                case R.id.btn_reject:
                    break;
            }
        }
    }
    public ProfileAdapter(List<Groups> groups, Context context) {
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
        View invite_view_holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_group_admin_pending_holder, parent, false);
        RecyclerView.ViewHolder viewHolder = new InviteHolder(invite_view_holder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(groups.get(position).getBackground_image_url()).transform(new CircleTransform()).into(((ProfileAdapter.InviteHolder) holder).iv_picture);
        ((ProfileAdapter.InviteHolder) holder).txt_name.setText(groups.get(position).getGroup_name());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
