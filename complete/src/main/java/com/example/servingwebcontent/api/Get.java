package com.example.servingwebcontent.api;

import com.example.servingwebcontent.apiclasses.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public abstract class Get<E>{
    public abstract Collection<E> getApi() throws IOException, InterruptedException, ParseException;
}
