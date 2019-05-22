package com.api.core.utils;

import io.restassured.http.Method;

import java.util.Map;
import java.util.function.Consumer;

public class RequestBuilder {

    public RequestBuilder() {

    }

    public Map<String, Object> QueryParameters;

    public String ApiPath;
    public Method RequestType;
    public Map<String, String> Headers;
    public String ContentType;
    public String RequestBody;

    public String BaseUrl;
    public int Port;

    /** +
     *
     * @param builderFunction
     * @return
     */
    public RequestBuilder With(
            Consumer<RequestBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Request buildRequestObject() {

        return new Request(BaseUrl,ApiPath, RequestType, Headers, RequestBody, QueryParameters,ContentType
                );
    }

}
