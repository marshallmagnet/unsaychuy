package com.whatsbest.nexusbond.wassup.Classes;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class Users
{
    private String display_name, email_address, photo_url, cover_photo_url, user_id;

    public Users()
    {

    }

    public Users(String display_name, String email_address, String photo_url, String cover_photo_url)
    {
        this.display_name = display_name;
        this.email_address = email_address;
        this.photo_url = photo_url;
        this.cover_photo_url = cover_photo_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getCover_photo_url() {
        return cover_photo_url;
    }

    public void setCover_photo_url(String cover_photo_url) {
        this.cover_photo_url = cover_photo_url;
    }
}
