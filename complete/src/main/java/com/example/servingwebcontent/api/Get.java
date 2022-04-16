package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.User;

import java.io.IOException;

public abstract class Get {
    public abstract User getApi() throws IOException, InterruptedException;
}
