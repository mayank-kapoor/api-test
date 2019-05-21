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




public class WheatherApiTest extends TestBase {

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
    @Test(priority = 1, dataProvider = "getTestData", dataProviderClass = ResourceLoader.class)
    public void verifyStatusCodeForLatLonApi(ITestContext testContext,JSONObject testDataObj) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(RequestConstants.LAT, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.LAT));
        queryParams.put(RequestConstants.LON, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.LON));
        queryParams.put(RequestConstants.APPID, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.APPID));

        Request apiRequest = buildApiRequest(queryParams);

        try {
            Response apiResponse = requestExecutor.executeRequest(apiRequest);
            testContext = getiTextContextAttribs(testContext, apiRequest, apiResponse);
            assertThat("status code is not 200", apiResponse.getStatusCode(), is(equalTo(ResponseConstants.STATUS_CODE_OK)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** +
     *
     * @param testContext
     * @param testDataObj
     */
    @Test(priority = 2, description = "Test to Assert Lat long in Weather Api", dataProvider = "getTestData", dataProviderClass = ResourceLoader.class,
            dependsOnMethods = "verifyStatusCodeForLatLonApi")
    public void verifyLatLonAsPerRequest(ITestContext testContext,JSONObject testDataObj) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(RequestConstants.LAT, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.LAT));
        queryParams.put(RequestConstants.LON, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.LON));
        queryParams.put(RequestConstants.APPID, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.APPID));
        Request apiRequest = buildApiRequest(queryParams);

        // rounding of the lat/lon in request params as response return upto 2 places of decimals

        float lat = roundoffAndReturn(testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).getFloat(RequestConstants.LAT));
        float lon = roundoffAndReturn(testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).getFloat(RequestConstants.LON));
        try {
            Response apiResponse = requestExecutor.executeRequest(apiRequest);
            testContext = getiTextContextAttribs(testContext, apiRequest, apiResponse);
            HashMap<String, Object> cordMap = (HashMap<String, Object>) apiResponse.jsonPath().getJsonObject("coord");
            assertThat(cordMap.get("lat"), is(equalTo(lat)));
            assertThat(cordMap.get("lon"), is(equalTo(lon)));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /** +
     *
     * @param testContext
     * @param testDataObj
     */
    @Test(priority = 3, dataProvider = "getTestData", dataProviderClass = ResourceLoader.class)
    public void verifyStatusCodeForZipCodeApi(ITestContext testContext , JSONObject testDataObj) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(RequestConstants.ZIP_CODE, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.ZIP_CODE));
        queryParams.put(RequestConstants.APPID, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.APPID));

        Request apiRequest = buildApiRequest(queryParams);

        try {
            Response apiResponse = requestExecutor.executeRequest(apiRequest);
            testContext = getiTextContextAttribs(testContext, apiRequest, apiResponse);
            assertThat(apiResponse.getStatusCode(), is(equalTo(ResponseConstants.STATUS_CODE_OK)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** +
     *
     * @param testContext
     * @param testDataObj
     */
    @Test(priority = 4, dataProvider = "getTestData", dataProviderClass = ResourceLoader.class,
            dependsOnMethods = "verifyStatusCodeForZipCodeApi")
    public void getTheWeatherObject(ITestContext testContext, JSONObject testDataObj) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put(RequestConstants.ZIP_CODE, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.ZIP_CODE));
        queryParams.put(RequestConstants.APPID, testDataObj.getJSONObject(RequestConstants.REQUEST_OBJ).get(RequestConstants.APPID));

        Request apiRequest = buildApiRequest(queryParams);

        try {
            Response apiResponse = requestExecutor.executeRequest(apiRequest);
            testContext = getiTextContextAttribs(testContext, apiRequest, apiResponse);
            //parsing and printing the weather object from response

            ArrayList<HashMap<String, Object>> weatherArrayList = apiResponse.jsonPath().get("weather");
            weatherArrayList.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        RestAssured.reset();
    }
}
