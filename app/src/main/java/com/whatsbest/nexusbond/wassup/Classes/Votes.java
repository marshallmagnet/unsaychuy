package com.whatsbest.nexusbond.wassup.Classes;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class Votes {
    private String user_id, image_id;
    private int total_votes;

    public Votes() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public int getTotal_votes() {
        return total_votes;
    }

    public void setTotal_votes(int total_votes) {
        this.total_votes = total_votes;
    }
}