package com.example.servingwebcontent.api;

public class UserFactory implements APIfactory{
    private String api;

    public UserFactory(String api) {
        this.api = api;
    }

    @Override
    public Get createGet() {
        return new UserGet(this.api);
    }

    @Override
    public Post createPost() {
        return null;
    }
}
