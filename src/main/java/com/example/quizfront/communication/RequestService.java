package com.example.quizfront.communication;

import java.net.HttpURLConnection;

public class RequestService {
    private HttpURLConnection httpURLConnection;
    private ObjectMapper objectMapper;

    public RequestService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
