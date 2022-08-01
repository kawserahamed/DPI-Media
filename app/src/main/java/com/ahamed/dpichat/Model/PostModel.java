package com.ahamed.dpichat.Model;

public class PostModel {


    private String id;
    private String imageUrl;
    private String name;
    private String post;


    public PostModel(String id, String imageUrl, String name, String post) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.post = post;
    }

    public PostModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
