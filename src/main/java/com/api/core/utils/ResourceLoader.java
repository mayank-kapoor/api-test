package com.api.core.utils;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class ResourceLoader extends Base {

    /**
     * +
     *
     * @param m
     * @return
     * @throws JSONException
     */
    @DataProvider
    public Object[][] getTestData(Method m) throws JSONException {
        String testClassName = m.getDeclaringClass().getSimpleName().split("Test")[0];
        String methodName = m.getName();
        String fileName = Constants.TEST_CASE_DIR + testClassName.toLowerCase() + Constants.JSON_EXT;
        JSONObject testDataJsonObject = new JSONObject(covertFileToString(fileName));
        JSONArray testCaseJsonArray = testDataJsonObject.getJSONArray(methodName);
        Object[][] testDataObj = new Object[testCaseJsonArray.length()][1];
        for (int i = 0; i < testCaseJsonArray.length(); i++) {
            testDataObj[i][0] = testCaseJsonArray.getJSONObject(i);
        }
        return testDataObj;
    }


    @DataProvider
    public Object[][] getFilteredTestData(Method m) throws JSONException {
        String testClassName = m.getDeclaringClass().getSimpleName().split("Test")[0];
        String methodName = m.getName();
        String fileName = Constants.TEST_CASE_DIR + testClassName.toLowerCase() + Constants.JSON_EXT;
        JSONObject testDataJsonObject = new JSONObject(covertFileToString(fileName));
        JSONArray testCaseJsonArray = testDataJsonObject.getJSONArray(methodName).getJSONObject(0).getJSONArray("data");
        JSONArray finalJsonArray = new JSONArray();
        String includePattern = System.getProperty("dataType");

        for (Object filteredObj : testCaseJsonArray) {
            JSONObject filteredJSONObj = (JSONObject) filteredObj;
            if (filteredJSONObj != null && filteredJSONObj.get("dataType").toString().equalsIgnoreCase(includePattern))
                finalJsonArray.put(filteredJSONObj);
        }
        Object[][] testDataObj = new Object[finalJsonArray.length()][1];
        for (int i = 0; i < finalJsonArray.length(); i++)

            testDataObj[i][0] = finalJsonArray.getJSONObject(i);

        return testDataObj;
    }

}