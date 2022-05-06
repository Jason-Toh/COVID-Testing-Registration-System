package com.example.servingwebcontent.api;

import java.io.IOException;

public abstract class Delete {
    public abstract void deleteApi(String id) throws IOException, InterruptedException;
}
