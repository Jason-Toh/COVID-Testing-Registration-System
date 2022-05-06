package com.example.servingwebcontent.api;

import java.io.IOException;

public abstract class Patch {
    public abstract String patchApi() throws IOException, InterruptedException;
}
