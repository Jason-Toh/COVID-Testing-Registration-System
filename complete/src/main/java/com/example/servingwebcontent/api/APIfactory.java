package com.example.servingwebcontent.api;

public interface APIfactory {
    public Get createGet();

    public Post createPost();

    // ---DELETE AND PATCH CAN BE IMPLEMENT IN FUTURE---

        //    public Delete createDelete();

    public Patch createPatch();
}
