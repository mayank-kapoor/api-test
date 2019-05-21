package com.api.core.utils;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class RequestExecutor {
    /** +
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Response executeRequest(Request request) throws Exception {
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        System.setProperty(
                "jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification requestSpecification = generateRequestSpec(request);
        Response response = given().spec(requestSpecification).request(request.getRequestType());
        response = checkRedirection(response, request.getRequestType(), requestSpecification);
        return response;
    }

    /** +
     *
     * @param request
     * @return
     * @throws IOException
     */
    private RequestSpecification generateRequestSpec(Request request) throws IOException {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        if (request.getRequestBody() != null) {
            builder.setBody(request.getRequestBody());
        }
        if (request.getApiPath() != null) {
            builder.setBasePath(request.getApiPath());
        }
        if (request.getRequestBody() != null) {
            builder.setBody(request.getRequestBody());
        }
        if (request.getContentType() != null) {
            builder.setContentType(request.getContentType());
        }
        if (request.getHeaders() != null) {
            builder.addHeaders(request.getHeaders());
        }
        if (request.getQueryParameters() != null) {
            builder.addQueryParams(request.getQueryParameters());
        }

        if (request.getBaseUrl() != null) {
            builder.setBaseUri(request.getBaseUrl());
        }


        RequestSpecification requestSpec = builder.build();
        return requestSpec;
    }

    /** +
     *
     * @param response
     * @param requestType
     * @param requestSpecification
     * @return
     */

    private Response checkRedirection(Response response, Method requestType, RequestSpecification requestSpecification) {
        if (response.getStatusCode() == 301) {
            String redirectURL = response.getHeader(Constants.LOCATION);
            return given().spec(requestSpecification).request(requestType, redirectURL);
        }
        return response;
    }

}
