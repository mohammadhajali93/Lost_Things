package com.mohammadhajali.mychat22;


public class Comments {
    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_post() {
        return id_post;
    }

    public void setId_post(String id_post) {
        this.id_post = id_post;
    }

    private  String id_user;
    private  String id_comment;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private  String image;

    public String getId_comment() {
        return id_comment;
    }

    public void setId_comment(String id_comment) {
        this.id_comment = id_comment;
    }

    private  String id_post;
    private  String name;
    private  String text_comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText_comment() {
        return text_comment;
    }

    public void setText_comment(String text_comment) {
        this.text_comment = text_comment;
    }
}

