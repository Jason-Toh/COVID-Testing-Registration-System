package com.example.servingwebcontent.api;

import java.io.IOException;
import java.util.List;

public abstract class Patch {
    public abstract String patchApi(List<String> thingsToPatch) throws IOException, InterruptedException;
}
