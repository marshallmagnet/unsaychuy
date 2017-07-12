package com.whatsbest.nexusbond.wassup.DataHandler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.whatsbest.nexusbond.wassup.Classes.Groups;
import com.whatsbest.nexusbond.wassup.Classes.Images;
import com.whatsbest.nexusbond.wassup.Classes.Messages;
import com.whatsbest.nexusbond.wassup.Classes.Posts;
import com.whatsbest.nexusbond.wassup.Classes.Users;
import com.whatsbest.nexusbond.wassup.Classes.Votes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class DataSource {
    private List<Users> usersList = new ArrayList<>();
    private List<Posts> postsList = new ArrayList<>();
    private List<Images> imageList = new ArrayList<>();
    private List<Votes> votesList = new ArrayList<>();
    private List<Groups> groupsList = new ArrayList<>();
    private List<Messages> messagesList = new ArrayList<>();
    private List<Groups> adminList = new ArrayList<>();
    private List<Groups> memberList = new ArrayList<>();
    private List<Groups> pendingList = new ArrayList<>();
    private List<Groups> invitedList = new ArrayList<>();
    private List<Groups> mineList = new ArrayList<>();
    private Users users = new Users();
    private Posts posts = new Posts();
    private Images images = new Images();
    private Votes votes = new Votes();
    private Groups groups = new Groups();
    private Messages messages = new Messages();
    private String user_id;

    public DataSource() {

    }

    public void setPost_snap(DataSnapshot dataSnapshot) {

        posts = dataSnapshot.getValue(Posts.class);

        if (posts.isFinished() != true) {
            posts.setAuthor_info(dataSnapshot.child("author_info").getValue().toString());
            posts.splitAuthor_info();
            posts.setFrame_type(dataSnapshot.child("frame_type").getValue().toString());
            posts.setPost_description(dataSnapshot.child("post_description").getValue().toString());
            posts.setPost_id(dataSnapshot.getKey());
            posts.setFrame_one(dataSnapshot.child("frame_one").getValue().toString());
            posts.setFrame_two(dataSnapshot.child("frame_two").getValue().toString());

            if (posts.getFrame_type().equals("THREE_VERTICAL") || posts.getFrame_type().equals("THREE_HORIZONTAL")) {
                posts.setFrame_three(dataSnapshot.child("frame_three").getValue().toString());
                posts.setFrame_four("empty");
            } else if (posts.getFrame_type().equals("FOUR_CROSS")) {
                posts.setFrame_three(dataSnapshot.child("frame_three").getValue().toString());
                posts.setFrame_four(dataSnapshot.child("frame_four").getValue().toString());
            } else if (posts.getFrame_type().equals("TWO_VERTICAL") || posts.getFrame_type().equals("TWO_HORIZONTAL")) {
                posts.setFrame_three("empty");
                posts.setFrame_four("empty");
            }

            if (posts.getAuthor_id().equals(user_id)) {
                for (int indexu = 0; indexu < postsList.size(); indexu++) {
                    if (postsList.get(indexu).getPost_id().equals(dataSnapshot.getKey())) {
                        postsList.remove(indexu);
                        break;
                    }
                }
                postsList.add(posts);
            }

            for (int inner = 0; inner < usersList.size(); inner++) {
                if (usersList.get(inner).getUser_id().equals(posts.getAuthor_id())) {
                    postsList.add(posts);
                    break;
                }
            }
        }
        if (posts.isFinished() == true) {
            for (int index = 0; index < postsList.size(); index++) {
                if (postsList.get(index).getPost_id().equals(dataSnapshot.getKey())) {
                    postsList.remove(index);
                    break;
                }
            }
        }
    }

    public void setImage_snap(DataSnapshot dataSnapshot) {
        images = dataSnapshot.getValue(Images.class);
        images.setImage_id(dataSnapshot.getKey());

        for (int index = 0; index < postsList.size(); index++) {
            if (dataSnapshot.getKey().equals(postsList.get(index).getFrame_one())) {
                postsList.get(index).setFrame_number_one(images.getFrame_no());
                postsList.get(index).setFrame_one_url(images.getImage_url());
                postsList.get(index).setFrame_one_desc(images.getImage_description());
                break;
            } else if (dataSnapshot.getKey().equals(postsList.get(index).getFrame_two())) {
                postsList.get(index).setFrame_number_two(images.getFrame_no());
                postsList.get(index).setFrame_two_url(images.getImage_url());
                postsList.get(index).setFrame_two_desc(images.getImage_description());
                break;
            } else if (dataSnapshot.getKey().equals(postsList.get(index).getFrame_three())) {
                postsList.get(index).setFrame_number_three(images.getFrame_no());
                postsList.get(index).setFrame_three_url(images.getImage_url());
                postsList.get(index).setFrame_three_desc(images.getImage_description());
                break;
            } else if (dataSnapshot.getKey().equals(postsList.get(index).getFrame_four())) {
                postsList.get(index).setFrame_number_four(images.getFrame_no());
                postsList.get(index).setFrame_four_url(images.getImage_url());
                postsList.get(index).setFrame_four_desc(images.getImage_description());
                break;
            }
        }
    }

    public void setUser_snap(DataSnapshot dataSnapshot) {
        usersList.clear();
        postsList.clear();
        imageList.clear();
        adminList.clear();
        memberList.clear();
        pendingList.clear();
        for (DataSnapshot poSnap : dataSnapshot.getChildren()) {
            users = new Users();
            if (poSnap.hasChild("display_name") && poSnap.hasChild("photo_url")) {
                users.setDisplay_name(poSnap.child("display_name").getValue().toString());
                users.setPhoto_url(poSnap.child("photo_url").getValue().toString());
            }
            users.setUser_id(poSnap.getKey());
            usersList.add(users);
        }
    }

    public void setActivity_snap(DataSnapshot dataSnapshot) {
        posts = dataSnapshot.getValue(Posts.class);

        if (posts.isFinished() != true) {
            posts.setAuthor_info(dataSnapshot.child("author_info").getValue().toString());
            posts.splitAuthor_info();
            posts.setFrame_type(dataSnapshot.child("frame_type").getValue().toString());
            posts.setPost_description(dataSnapshot.child("post_description").getValue().toString());
            posts.setPost_id(dataSnapshot.getKey());
            posts.setFrame_one(dataSnapshot.child("frame_one").getValue().toString());
            posts.setFrame_two(dataSnapshot.child("frame_two").getValue().toString());

            if (posts.getFrame_type().equals("THREE_VERTICAL") || posts.getFrame_type().equals("THREE_HORIZONTAL")) {
                posts.setFrame_three(dataSnapshot.child("frame_three").getValue().toString());
                posts.setFrame_four("empty");
            } else if (posts.getFrame_type().equals("FOUR_CROSS")) {
                posts.setFrame_three(dataSnapshot.child("frame_three").getValue().toString());
                posts.setFrame_four(dataSnapshot.child("frame_four").getValue().toString());
            } else if (posts.getFrame_type().equals("TWO_VERTICAL") || posts.getFrame_type().equals("TWO_HORIZONTAL")) {
                posts.setFrame_three("empty");
                posts.setFrame_four("empty");
            }

            if (!posts.getAuthor_id().equals(user_id)) {
                postsList.add(posts);
            }
        }
    }

    public void setVote_snap(DataSnapshot dataSnapshot) {
        for (DataSnapshot poSnap : dataSnapshot.getChildren()) {
            votes = new Votes();
            votes.setImage_id(dataSnapshot.getKey());
            votes.setUser_id(poSnap.getKey());
            votes.setTotal_votes((int) (long) dataSnapshot.getChildrenCount());

            for (int inneru = 0; inneru < postsList.size(); inneru++) {
                if (postsList.get(inneru).getFrame_one().equals(votes.getImage_id())) {
                    postsList.get(inneru).setFrame_one_votes(votes.getTotal_votes());
                    if (votes.getUser_id().equals(user_id)) {
                        postsList.get(inneru).setVoted(true);
                    }
                    break;
                } else if (postsList.get(inneru).getFrame_two().equals(votes.getImage_id())) {
                    postsList.get(inneru).setFrame_two_votes(votes.getTotal_votes());
                    if (votes.getUser_id().equals(user_id)) {
                        postsList.get(inneru).setVoted(true);
                    }
                    break;
                } else if (postsList.get(inneru).getFrame_three().equals(votes.getImage_id())) {
                    postsList.get(inneru).setFrame_three_votes(votes.getTotal_votes());
                    if (votes.getUser_id().equals(user_id)) {
                        postsList.get(inneru).setVoted(true);
                    }
                    break;
                } else if (postsList.get(inneru).getFrame_four().equals(votes.getImage_id())) {
                    postsList.get(inneru).setFrame_four_votes(votes.getTotal_votes());
                    if (votes.getUser_id().equals(user_id)) {
                        postsList.get(inneru).setVoted(true);
                    }
                    break;
                }
            }
        }
    }

    public void setGroup_snaps(DataSnapshot dataSnapshot) {
        groupsList.clear();
        mineList.clear();
        for (DataSnapshot poSnap : dataSnapshot.getChildren()) {
            groups = poSnap.getValue(Groups.class);
            groups.setGroup_id(poSnap.getKey());

            if (poSnap.child("admin_members").hasChild(user_id)) {
                groups.setAdmin_status(true);
            }

            if (poSnap.hasChild("pending_members")) {
                if (poSnap.child("pending_members").hasChild(user_id)) {
                    groups.setPending_status(true);
                }
            }

            if (poSnap.hasChild("members")) {
                if (poSnap.child("members").hasChild(user_id)) {
                    groups.setMember_status(true);
                }
            }

            if (groups.isPrivate_status() == false) {

                if (groups.isAdmin_status() == true || groups.isMember_status() == true) {
                    mineList.add(groups);
                } else {
                    groupsList.add(groups);
                }
            }
        }
    }

    public void setMessages_snap(DataSnapshot dataSnapshot) {
        messages = new Messages();
        messages.setMessage_id(dataSnapshot.getKey());
        messages.setSender_id(dataSnapshot.child("sender_id").getValue().toString());

        if (dataSnapshot.hasChild("photo_url")) {
            messages.setPhoto_url(dataSnapshot.child("photo_url").getValue().toString());
            messages.setText_type(false);
        }

        if (dataSnapshot.hasChild("text")) {
            messages.setText(dataSnapshot.child("text").getValue().toString());
            messages.setText_type(true);
        }

        if (dataSnapshot.hasChild("sender_name")) {
            messages.setSender_name(dataSnapshot.child("sender_name").getValue().toString());
        }

        if (dataSnapshot.child("sender_id").getValue().toString().equals(user_id)) {
            messages.setSender(true);
        } else if (!dataSnapshot.child("sender_id").equals(user_id)) {
            messages.setSender(false);
        }
        messagesList.add(messages);
    }

    public void setChatSender_snap(DataSnapshot dataSnapshot) {
        for (int index = 0; index < messagesList.size(); index++) {
            if (messagesList.get(index).getSender_id().equals(dataSnapshot.getKey())) {
                messagesList.get(index).setSender_photo_url(dataSnapshot.child("photo_url").getValue().toString());
            }
        }
    }

    public void setMember_admin_snap(DataSnapshot dataSnapshot, String tag) {

        groups = new Groups();
        groups.setMember_id(dataSnapshot.getKey());
        groups.setAdmin_status(true);
        groups.setMember_status(false);
        groups.setPending_status(false);
        groups.setInvite_status(false);
        groups.setGroup_id(dataSnapshot.getRef().getParent().getParent().getKey());

        if (tag.equals("Added")) {
            if (groups.getMember_id().equals(user_id)) {
                groups.setAdmin_viewer(true);
            } else if (!groups.getMember_id().equals(user_id)) {
                groups.setAdmin_viewer(false);
            }

            for (int index = 0; index < usersList.size(); index++) {
                if (groups.getMember_id().equals(usersList.get(index).getUser_id())) {
                    groups.setMember_name(usersList.get(index).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(index).getPhoto_url());

                    adminList.add(groups);
                    break;
                }
            }
        }

        if (tag.equals("Removed")) {
            for (int check = 0; check < adminList.size(); check++) {
                if (dataSnapshot.getKey().equals(adminList.get(check).getMember_id())) {
                    adminList.remove(check);
                    break;
                }
            }
        }
    }

    public void setMember_snap(DataSnapshot dataSnapshot, String tag) {
        groups = new Groups();
        groups.setMember_id(dataSnapshot.getKey());
        groups.setMember_status(true);
        groups.setPending_status(false);
        groups.setAdmin_status(false);
        groups.setInvite_status(false);
        groups.setGroup_id(dataSnapshot.getRef().getParent().getParent().getKey());

        if (tag.equals("Added")) {
            for (int admin = 0; admin < adminList.size(); admin++) {
                if (user_id.equals(adminList.get(admin).getMember_id())) {
                    groups.setAdmin_viewer(true);
                    break;
                }
            }

            for (int index = 0; index < usersList.size(); index++) {
                if (groups.getMember_id().equals(usersList.get(index).getUser_id())) {
                    groups.setMember_name(usersList.get(index).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(index).getPhoto_url());
                    memberList.add(groups);
                    break;
                }
            }
        }

        if (tag.equals("Removed")) {
            for (int check = 0; check < memberList.size(); check++) {
                if (dataSnapshot.getKey().equals(memberList.get(check).getMember_id())) {
                    memberList.remove(check);
                    break;
                }
            }
        }
    }

    public void setPending_snap(DataSnapshot dataSnapshot, String tag) {
        groups = new Groups();
        groups.setMember_id(dataSnapshot.getKey());
        groups.setPending_status(true);
        groups.setAdmin_status(false);
        groups.setMember_status(false);
        groups.setInvite_status(false);
        groups.setGroup_id(dataSnapshot.getRef().getParent().getParent().getKey());

        if (tag.equals("Added")) {
            for (int admin = 0; admin < adminList.size(); admin++) {
                if (user_id.equals(adminList.get(admin).getMember_id())) {
                    groups.setAdmin_viewer(true);
                    break;
                }
            }

            for (int index = 0; index < usersList.size(); index++) {
                if (groups.getMember_id().equals(usersList.get(index).getUser_id())) {
                    groups.setMember_name(usersList.get(index).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(index).getPhoto_url());
                    pendingList.add(groups);
                    break;
                }
            }
        }

        if (tag.equals("Removed")) {
            for (int check = 0; check < pendingList.size(); check++) {
                if (dataSnapshot.getKey().equals(pendingList.get(check).getMember_id())) {
                    pendingList.remove(check);
                    break;
                }
            }
        }
    }

    public void setSearch_snap(DataSnapshot dataSnapshot, String key_word) {
        groups = new Groups();
        groups.setGroup_id(dataSnapshot.getKey());
        groups.setBackground_image_url(dataSnapshot.child("background_image_url").getValue().toString());
        groups.setGroup_name(dataSnapshot.child("group_name").getValue().toString());
        groups.setGroup_description(dataSnapshot.child("group_description").getValue().toString());
        groups.setPrivate_status((Boolean) dataSnapshot.child("private_status").getValue());

        if (dataSnapshot.child("admin_members").hasChild(user_id)) {
            groups.setAdmin_status(true);
        }

        if (dataSnapshot.hasChild("pending_members")) {
            if (dataSnapshot.child("pending_members").hasChild(user_id)) {
                groups.setPending_status(true);
            }
        }

        if (dataSnapshot.hasChild("members")) {
            if (dataSnapshot.child("members").hasChild(user_id)) {
                groups.setMember_status(true);
            }
        }

        if (groups.isPrivate_status() == false) {

            if (groups.isAdmin_status() == false && groups.isMember_status() == false) {
                if ((groups.getGroup_description().toLowerCase().indexOf(key_word) > 0 || groups.getGroup_name().toLowerCase().indexOf(key_word) > 0) || (groups.getGroup_description().toLowerCase().contains(key_word) || groups.getGroup_name().toLowerCase().contains(key_word))
                        || groups.getGroup_description().toLowerCase().matches(key_word) || groups.getGroup_name().toLowerCase().matches(key_word)) {
                    groupsList.add(groups);
                }
            }
        }
    }

    public void setMemberUser_snap(DataSnapshot dataSnapshot) {
        invitedList.clear();
        groupsList.clear();
        for (int user = 0; user < usersList.size(); user++) {

            groups = new Groups();

            groups.setGroup_id(dataSnapshot.getRef().getKey());

            if (dataSnapshot.hasChild("invited_pending_members")) {
                if (dataSnapshot.child("invited_pending_members").hasChild(usersList.get(user).getUser_id())) {
                    groups.setMember_id(usersList.get(user).getUser_id());
                    groups.setMember_name(usersList.get(user).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(user).getPhoto_url());
                    groups.setPending_invite(true);
                    groups.setInvite_status(true);
                    groups.setAdmin_viewer(true);
                }
            } else {
                groups.setPending_invite(false);
            }

            if (dataSnapshot.hasChild("admin_members")) {
                if (dataSnapshot.child("admin_members").hasChild(usersList.get(user).getUser_id())) {
                    groups.setMember_id(usersList.get(user).getUser_id());
                    groups.setMember_name(usersList.get(user).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(user).getPhoto_url());
                    groups.setAdmin_status(true);
                    groups.setInvite_status(false);
                    groups.setAdmin_viewer(true);
                }
            } else {
                groups.setAdmin_status(false);
            }

            if (dataSnapshot.hasChild("members")) {
                if (dataSnapshot.child("members").hasChild(usersList.get(user).getUser_id())) {
                    groups.setMember_id(usersList.get(user).getUser_id());
                    groups.setMember_name(usersList.get(user).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(user).getPhoto_url());
                    groups.setMember_status(true);
                    groups.setInvite_status(false);
                    groups.setAdmin_viewer(true);
                }
            } else {
                groups.setMember_status(false);
            }

            if (dataSnapshot.hasChild("pending_members")) {
                if (dataSnapshot.child("pending_members").hasChild(usersList.get(user).getUser_id())) {
                    groups.setMember_id(usersList.get(user).getUser_id());
                    groups.setMember_name(usersList.get(user).getDisplay_name());
                    groups.setMember_photo_url(usersList.get(user).getPhoto_url());
                    groups.setPending_status(true);
                    groups.setAdmin_viewer(true);
                }
            } else {
                groups.setPending_status(false);
            }

            if (groups.isPending_invite() == false && groups.isMember_status() == false && groups.isAdmin_status() == false && groups.isPending_status() == false) {
                groups.setMember_id(usersList.get(user).getUser_id());
                groups.setMember_name(usersList.get(user).getDisplay_name());
                groups.setMember_photo_url(usersList.get(user).getPhoto_url());
                groups.setInvite_status(true);
                groups.setAdmin_viewer(true);
            }
            Log.d("See", "Lots " + groups.getMember_name());
            groupsList.add(groups);
        }
    }

    public void searchMember(String key_word) {
        invitedList.clear();
        for (int list = 0; list < groupsList.size(); list++) {
            if (groupsList.get(list).getMember_name().toString().toLowerCase().indexOf(key_word) > 0 || groupsList.get(list).getMember_name().toString().toLowerCase().contains(key_word) || groupsList.get(list).getMember_name().toLowerCase().toString().matches(key_word)) {
                groups = new Groups();
                groups.setGroup_id(groupsList.get(list).getGroup_id());
                groups.setMember_id(groupsList.get(list).getMember_id());
                groups.setMember_name(groupsList.get(list).getMember_name());
                groups.setMember_photo_url(groupsList.get(list).getMember_photo_url());
                groups.setAdmin_status(groupsList.get(list).isAdmin_status());
                groups.setMember_status(groupsList.get(list).isMember_status());
                groups.setPending_status(groupsList.get(list).isPending_status());
                groups.setAdmin_viewer(groupsList.get(list).isAdmin_viewer());
                groups.setInvite_status(groupsList.get(list).isInvite_status());
                groups.setPending_invite(groupsList.get(list).isPending_invite());
                invitedList.add(groups);
            }
        }
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Users> getUsers_data() {
        return usersList;
    }

    public List<Posts> getPosts_data() {
        return postsList;
    }

    public List<Images> getImages_data() {
        return imageList;
    }

    public List<Votes> getVotesList() {
        return votesList;
    }

    public List<Groups> getGroupsList() {
        return groupsList;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public List<Groups> getAdminList() {
        return adminList;
    }

    public List<Groups> getMemberList() {
        return memberList;
    }

    public List<Groups> getPendingList() {
        return pendingList;
    }

    public List<Groups> getMineList() {
        return mineList;
    }

    public List<Groups> getInvitedList() {
        return invitedList;
    }
}

