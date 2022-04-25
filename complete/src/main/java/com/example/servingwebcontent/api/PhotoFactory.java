package com.example.servingwebcontent.api;

public class PhotoFactory implements APIfactory{
    private String api;
    private String randomString;

    public PhotoFactory(String api, String randomString) {
        this.api = api;
        this.randomString = randomString;
    }

    @Override
    public Get createGet() {
        return null;
    }

    @Override
    public Post createPost() {
        return new PhotoPost(this.api, this.randomString);
    }
}
