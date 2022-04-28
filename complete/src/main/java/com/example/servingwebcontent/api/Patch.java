package com.example.servingwebcontent.api;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collection;

public abstract class Patch{
    public abstract String patchApi() throws IOException, InterruptedException;
}
