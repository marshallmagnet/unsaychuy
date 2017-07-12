package com.whatsbest.nexusbond.wassup.Classes;

import com.google.firebase.database.Exclude;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class Messages {
    private String message_id, text, sender_id, sender_name, photo_url, sender_photo_url;
    private boolean sender, text_type;

    public Messages() {
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Exclude
    public boolean isSender() {
        return sender;
    }

    @Exclude
    public void setSender(boolean sender) {
        this.sender = sender;
    }

    @Exclude
    public boolean isText_type() {
        return text_type;
    }

    @Exclude
    public void setText_type(boolean text_type) {
        this.text_type = text_type;
    }

    @Exclude
    public String getSender_photo_url() {
        return sender_photo_url;
    }

    @Exclude
    public void setSender_photo_url(String sender_photo_url) {
        this.sender_photo_url = sender_photo_url;
    }
}
