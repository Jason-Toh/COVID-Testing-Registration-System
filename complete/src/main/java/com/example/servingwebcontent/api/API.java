package com.example.servingwebcontent.api;

import io.github.cdimascio.dotenv.Dotenv;

public class API {
    public static String getAPIKey() {
        Dotenv dotenv = Dotenv.load();
        String API_KEY = dotenv.get("API_KEY");
        return API_KEY;
    }
}
