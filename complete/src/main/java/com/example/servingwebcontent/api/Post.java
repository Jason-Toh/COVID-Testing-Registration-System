package com.example.servingwebcontent.api;

import java.io.IOException;
import java.util.List;

public abstract class Post {
    public abstract String postApi(List<String> thingsToPost) throws IOException, InterruptedException;
}
