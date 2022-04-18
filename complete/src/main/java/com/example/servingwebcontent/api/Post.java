package com.example.servingwebcontent.api;

import java.io.IOException;

public abstract class Post {
    public abstract String postApi() throws IOException, InterruptedException;
}
