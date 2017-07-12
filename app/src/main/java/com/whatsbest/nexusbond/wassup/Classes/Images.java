package com.whatsbest.nexusbond.wassup.Classes;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */

public class Images
{
    private int frame_no;
    private  String image_description, image_url, image_id;

    public Images()
    {

    }

    public Images(int frame_no, String image_description, String image_url) {
        this.frame_no = frame_no;
        this.image_description = image_description;
        this.image_url = image_url;
    }

    public Images(int frame_no, String image_description, String image_url, String image_id) {
        this.frame_no = frame_no;
        this.image_description = image_description;
        this.image_url = image_url;
        this.image_id = image_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public int getFrame_no() {
        return frame_no;
    }

    public void setFrame_no(int frame_no) {
        this.frame_no = frame_no;
    }

    public String getImage_description() {
        return image_description;
    }

    public void setImage_description(String image_description) {
        this.image_description = image_description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
