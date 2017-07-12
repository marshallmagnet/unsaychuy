package com.whatsbest.nexusbond.wassup.Classes;

import com.google.firebase.database.Exclude;

/**
 * Created by NexusNinja2 on 7/10/2017.
 */


public class Posts {
    private String author_info, frame_one, frame_two, frame_three, frame_four, frame_type, post_description, post_id;
    private String[] author_information;
    private String author_name, author_email, author_photo_url, author_id;
    private boolean private_status, finished, voted;
    private int frame_number_one, frame_number_two, frame_number_three, frame_number_four;
    private int frame_one_votes, frame_two_votes, frame_three_votes, frame_four_votes;
    private Long timestamp;
    private String frame_one_desc, frame_two_desc, frame_three_desc, frame_four_desc;
    private String frame_one_url, frame_two_url, frame_three_url, frame_four_url;

    public Posts() {

    }

    public void splitAuthor_info() {
        author_information = author_info.split(",");
        author_name = author_information[0];
        author_email = author_information[1];
        author_photo_url = author_information[2];
        author_id = author_information[3];
    }

    public Posts(String author_info, String frame_one, String frame_two, String frame_three, String frame_four, String frame_type, String post_description, boolean private_status, boolean finished, Long timestamp) {
        this.author_info = author_info;
        this.frame_one = frame_one;
        this.frame_two = frame_two;
        this.frame_three = frame_three;
        this.frame_four = frame_four;
        this.frame_type = frame_type;
        this.post_description = post_description;
        this.private_status = private_status;
        this.finished = finished;
        this.timestamp = timestamp;
    }

    public String getFrame_one_desc() {
        return frame_one_desc;
    }

    public void setFrame_one_desc(String frame_one_desc) {
        this.frame_one_desc = frame_one_desc;
    }

    public String getFrame_two_desc() {
        return frame_two_desc;
    }

    public void setFrame_two_desc(String frame_two_desc) {
        this.frame_two_desc = frame_two_desc;
    }

    public String getFrame_three_desc() {
        return frame_three_desc;
    }

    public void setFrame_three_desc(String frame_three_desc) {
        this.frame_three_desc = frame_three_desc;
    }

    public String getFrame_four_desc() {
        return frame_four_desc;
    }

    public void setFrame_four_desc(String frame_four_desc) {
        this.frame_four_desc = frame_four_desc;
    }

    public String getFrame_one_url() {
        return frame_one_url;
    }

    public void setFrame_one_url(String frame_one_url) {
        this.frame_one_url = frame_one_url;
    }

    public String getFrame_two_url() {
        return frame_two_url;
    }

    public void setFrame_two_url(String frame_two_url) {
        this.frame_two_url = frame_two_url;
    }

    public String getFrame_three_url() {
        return frame_three_url;
    }

    public void setFrame_three_url(String frame_three_url) {
        this.frame_three_url = frame_three_url;
    }

    public String getFrame_four_url() {
        return frame_four_url;
    }

    public void setFrame_four_url(String frame_four_url) {
        this.frame_four_url = frame_four_url;
    }

    public boolean isPrivate_status() {
        return private_status;
    }

    public void setPrivate_status(boolean private_status) {
        this.private_status = private_status;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String[] getAuthor_information() {
        return author_information;
    }

    public void setAuthor_information(String[] author_information) {
        this.author_information = author_information;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthor_photo_url() {
        return author_photo_url;
    }

    public void setAuthor_photo_url(String author_photo_url) {
        this.author_photo_url = author_photo_url;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_info() {
        return author_info;
    }

    public void setAuthor_info(String author_info) {
        this.author_info = author_info;
    }

    public String getFrame_one() {
        return frame_one;
    }

    public void setFrame_one(String frame_one) {
        this.frame_one = frame_one;
    }

    public String getFrame_two() {
        return frame_two;
    }

    public void setFrame_two(String frame_two) {
        this.frame_two = frame_two;
    }

    public String getFrame_three() {
        return frame_three;
    }

    public void setFrame_three(String frame_three) {
        this.frame_three = frame_three;
    }

    public String getFrame_four() {
        return frame_four;
    }

    public void setFrame_four(String frame_four) {
        this.frame_four = frame_four;
    }

    public String getFrame_type() {
        return frame_type;
    }

    public void setFrame_type(String frame_type) {
        this.frame_type = frame_type;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Exclude
    public boolean isVoted() {
        return voted;
    }

    @Exclude
    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    @Exclude
    public int getFrame_one_votes() {
        return frame_one_votes;
    }

    @Exclude
    public void setFrame_one_votes(int frame_one_votes) {
        this.frame_one_votes = frame_one_votes;
    }

    @Exclude
    public int getFrame_two_votes() {
        return frame_two_votes;
    }

    @Exclude
    public void setFrame_two_votes(int frame_two_votes) {
        this.frame_two_votes = frame_two_votes;
    }

    @Exclude
    public int getFrame_three_votes() {
        return frame_three_votes;
    }

    @Exclude
    public void setFrame_three_votes(int frame_three_votes) {
        this.frame_three_votes = frame_three_votes;
    }

    @Exclude
    public int getFrame_four_votes() {
        return frame_four_votes;
    }

    @Exclude
    public void setFrame_four_votes(int frame_four_votes) {
        this.frame_four_votes = frame_four_votes;
    }

    @Exclude
    public int getFrame_number_one() {
        return frame_number_one;
    }

    @Exclude
    public void setFrame_number_one(int frame_number_one) {
        this.frame_number_one = frame_number_one;
    }

    @Exclude
    public int getFrame_number_two() {
        return frame_number_two;
    }

    @Exclude
    public void setFrame_number_two(int frame_number_two) {
        this.frame_number_two = frame_number_two;
    }

    @Exclude
    public int getFrame_number_three() {
        return frame_number_three;
    }

    @Exclude
    public void setFrame_number_three(int frame_number_three) {
        this.frame_number_three = frame_number_three;
    }

    @Exclude
    public int getFrame_number_four() {
        return frame_number_four;
    }

    @Exclude
    public void setFrame_number_four(int frame_number_four) {
        this.frame_number_four = frame_number_four;
    }
}
