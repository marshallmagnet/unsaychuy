package com.whatsbest.nexusbond.wassup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.whatsbest.nexusbond.wassup.Classes.Messages;
import com.whatsbest.nexusbond.wassup.R;
import com.whatsbest.nexusbond.wassup.Utilities.CircleTransform;

import java.util.List;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class GroupChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Messages> messages;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public class BlueHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_blue;
        TextView txt_message;

        public BlueHolder(View itemView) {
            super(itemView);

            ll_blue = (LinearLayout) itemView.findViewById(R.id.ll_blue);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
        }
    }

    public class YellowHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_blue;
        TextView txt_message;
        ImageView iv_pic;

        public YellowHolder(View itemView) {
            super(itemView);

            ll_blue = (LinearLayout) itemView.findViewById(R.id.ll_blue);
            txt_message = (TextView) itemView.findViewById(R.id.txt_message);
            iv_pic = (ImageView)itemView.findViewById(R.id.iv_pic);
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_blue, ll_yellow;
        ImageView iv_image, iv_pic;

        public ImageHolder(View itemView) {
            super(itemView);

            ll_blue = (LinearLayout) itemView.findViewById(R.id.ll_blue);
            ll_yellow = (LinearLayout) itemView.findViewById(R.id.ll_yellow);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            iv_pic = (ImageView)itemView.findViewById(R.id.iv_pic);
        }
    }

    public GroupChatAdapter(List<Messages> messages, Context context) {
        this.messages = messages;
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

        switch (viewType)
        {
            case 0:
                View blue_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_chat_bubble_blue, parent, false);
                viewHolder = new BlueHolder(blue_holder);
                break;
            case 1:
                View yellow_holder = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_chat_bubble_yellow, parent, false);
                viewHolder = new YellowHolder(yellow_holder);
                break;
            case 2:
                View image_blue = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_chat_image_bubble_blue, parent, false);
                viewHolder = new ImageHolder(image_blue);
                break;
            case 3:
                View image_yellow = LayoutInflater.from((parent.getContext())).inflate(R.layout.cardview_chat_image_bubble_yellow, parent, false);
                viewHolder = new ImageHolder(image_yellow);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType())
        {
            case 0:
                ((BlueHolder)holder).txt_message.setText(messages.get(position).getText());
                break;
            case 1:
                Picasso.with(context).load(messages.get(position).getSender_photo_url()).transform(new CircleTransform()).into(((YellowHolder) holder).iv_pic);
                ((YellowHolder)holder).txt_message.setText(messages.get(position).getText());
                break;
            case 2:
                Picasso.with(context).load(messages.get(position).getPhoto_url()).into(((ImageHolder) holder).iv_image);
                break;
            case 3:
                Picasso.with(context).load(messages.get(position).getSender_photo_url()).transform(new CircleTransform()).into(((ImageHolder) holder).iv_pic);
                Picasso.with(context).load(messages.get(position).getPhoto_url()).resize(150,150).onlyScaleDown().into(((ImageHolder) holder).iv_image);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        int int_value;

        if (messages.get(position).isSender() == true && messages.get(position).isText_type() == true) {
            int_value = 0;
        } else if (messages.get(position).isSender() != true && messages.get(position).isText_type() == true) {
            int_value = 1;
        } else if (messages.get(position).isSender() == true && messages.get(position).isText_type() != true) {
            int_value = 2;
        } else if (messages.get(position).isSender() != true && messages.get(position).isText_type() != true) {
            int_value = 3;
        } else {
            int_value = -1;
        }

        return int_value;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
