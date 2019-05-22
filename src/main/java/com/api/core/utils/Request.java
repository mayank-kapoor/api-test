package com.api.core.utils;

import lombok.Data;

import io.restassured.http.Method;
import java.util.Map;

@Data
public class Request {
    private Map<String, Object> queryParameters;
    private String apiPath;
    private String baseUrl;
    private Method requestType;
    private Map<String, String> headers;
    private String contentType;
    private String requestBody;


    public Request(String baseUrl,String apiPath, Method requestType,
                   Map<String, String> headers,
                   String requestBody, Map<String, Object> queryParameters, String contentType
    ) {
        this.baseUrl = baseUrl;
        this.requestType = requestType;
        this.apiPath = apiPath;
        this.headers = headers;
        this.contentType = contentType;
        this.requestBody = requestBody;
        this.queryParameters = queryParameters;

    }


}
