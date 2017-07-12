package com.whatsbest.nexusbond.wassup.Classes;

import com.google.firebase.database.Exclude;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class Groups {
    private String background_image_url, group_description, group_name, admin_id, group_id, member_id, member_name, member_photo_url;
    private boolean private_status, admin_status, member_status, pending_status, admin_viewer, invite_status, pending_invite;
    private Long timestamp;

    public Groups() {
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBackground_image_url() {
        return background_image_url;
    }

    public void setBackground_image_url(String background_image_url) {
        this.background_image_url = background_image_url;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public boolean isPrivate_status() {
        return private_status;
    }

    public void setPrivate_status(boolean private_status) {
        this.private_status = private_status;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    @Exclude
    public boolean isPending_status() {
        return pending_status;
    }

    @Exclude
    public void setPending_status(boolean pending_status) {
        this.pending_status = pending_status;
    }

    @Exclude
    public boolean isAdmin_status() {
        return admin_status;
    }

    @Exclude
    public void setAdmin_status(boolean admin_status) {
        this.admin_status = admin_status;
    }

    @Exclude
    public boolean isMember_status() {
        return member_status;
    }

    @Exclude
    public void setMember_status(boolean member_status) {
        this.member_status = member_status;
    }

    @Exclude
    public String getAdmin_id() {
        return admin_id;
    }

    @Exclude
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    @Exclude
    public String getMember_id() {
        return member_id;
    }

    @Exclude
    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    @Exclude
    public String getMember_name() {
        return member_name;
    }

    @Exclude
    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    @Exclude
    public String getMember_photo_url() {
        return member_photo_url;
    }

    @Exclude
    public void setMember_photo_url(String member_photo_url) {
        this.member_photo_url = member_photo_url;
    }

    @Exclude
    public boolean isAdmin_viewer() {
        return admin_viewer;
    }

    @Exclude
    public void setAdmin_viewer(boolean admin_viewer) {
        this.admin_viewer = admin_viewer;
    }

    @Exclude
    public boolean isInvite_status() {
        return invite_status;
    }

    @Exclude
    public void setInvite_status(boolean invite_status) {
        this.invite_status = invite_status;
    }

    @Exclude
    public boolean isPending_invite() {
        return pending_invite;
    }

    @Exclude
    public void setPending_invite(boolean pending_invite) {
        this.pending_invite = pending_invite;
    }
}