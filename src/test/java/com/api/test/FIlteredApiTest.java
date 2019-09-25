package com.api.test;

import com.api.core.utils.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class FIlteredApiTest extends TestBase {

    RequestExecutor requestExecutor;

    @BeforeClass
    public void init() {
        requestExecutor = new RequestExecutor();
        RestAssured.defaultParser = Parser.JSON;

    }

    /** +
     *
     * @param testContext
     * @param testDataObj
     */
    @Test(priority = 1, dataProvider = "getFilteredTestData", dataProviderClass = ResourceLoader.class)
    public void verifyStatusCode(ITestContext testContext,JSONObject testDataObj) {
        Map<String, Object> pathParams = new HashMap<>();
        if(testDataObj!=null)
        {
            pathParams.put("postId", testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get("postId"));
            Request apiRequest = buildApiRequest(pathParams);
            System.out.println("Request is :"+apiRequest.getBaseUrl()+apiRequest.getApiPath()+apiRequest.getPathParameters());

            try {
                Response apiResponse = requestExecutor.executeRequest(apiRequest);
                testContext = getiTextContextAttribs(testContext, apiRequest, apiResponse,testDataObj);
                assertThat("status code is not 200", apiResponse.getStatusCode(), is(equalTo(ResponseConstants.STATUS_CODE_OK)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }






    @AfterClass(alwaysRun = true)
    public void cleanup() {
        RestAssured.reset();
    }
}
