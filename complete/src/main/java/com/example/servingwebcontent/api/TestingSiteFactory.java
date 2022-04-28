package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.TestingSite;

public class TestingSiteFactory implements APIfactory{
    private String api;

    public TestingSiteFactory(String api) {
        this.api = api;
    }

    @Override
    public Get createGet() {
        return new TestingSiteGet(this.api);
    }

    @Override
    public Post createPost() {
        return null;
    }

    @Override
    public Patch createPatch() {
        return null;
    }
}
