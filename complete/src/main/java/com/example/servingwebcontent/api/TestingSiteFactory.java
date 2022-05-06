package com.example.servingwebcontent.api;

import com.example.servingwebcontent.models.TestingSite;

public class TestingSiteFactory implements APIfactory<TestingSite> {
    private String api;

    public TestingSiteFactory(String api) {
        this.api = api;
    }

    @Override
    public Get<TestingSite> createGet() {
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
