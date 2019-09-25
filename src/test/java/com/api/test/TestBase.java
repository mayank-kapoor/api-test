package com.api.test;

import com.api.core.utils.*;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {


    Properties props = new Properties();

    public TestBase() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * +
     *
     * @param pathParams
     * @return
     */
    public Request buildApiRequest(Map<String, Object> pathParams) {
        return new RequestBuilder().With(($) -> {
            $.BaseUrl = props.getProperty("api.url");
            $.ApiPath = props.getProperty("api.path");
            $.PathParams = pathParams;
            $.RequestType = Method.GET;
        }).buildRequestObject();
    }

    /**
     * +
     *
     * @param context
     * @param request
     * @param response
     * @return
     */
    protected ITestContext getiTextContextAttribs(ITestContext context, Request request, Response response, JSONObject testDataObj) {
        context.setAttribute(RequestConstants.REQUEST, request);
        context.setAttribute(ResponseConstants.RESPONSE, response);
        context.setAttribute(Constants.STATUS_CODE, response.getStatusCode());
        context.setAttribute(ResponseConstants.RESPONSE_TIME, response.getTimeIn(TimeUnit.MILLISECONDS));
        context.setAttribute("test-data", testDataObj);

        return context;
    }


    public float roundoffAndReturn(float d) {

        return (float) (Math.round(d * 100.0) / 100.0);


    }

}
