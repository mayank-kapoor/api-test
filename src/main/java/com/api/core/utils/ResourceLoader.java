package com.api.core.utils;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class ResourceLoader extends Base {

    /** +
     *
     * @param m
     * @return
     * @throws JSONException
     */
    @DataProvider
    public Object[][] getTestData(Method m) throws JSONException {
        String testClassName = m.getDeclaringClass().getSimpleName().split("Test")[0];
        String methodName = m.getName();
        String fileName = Constants.TEST_CASE_DIR+testClassName.toLowerCase()+ Constants.JSON_EXT;
        JSONObject testDataJsonObject = new JSONObject(covertFileToString(fileName));
        JSONArray testCaseJsonArray = testDataJsonObject.getJSONArray(methodName);
        Object[][] testDataObj = new Object[testCaseJsonArray.length()][1];
        for(int i = 0; i<testCaseJsonArray.length();i++){
            testDataObj[i][0] = testCaseJsonArray.getJSONObject(i);
        }
        return testDataObj;
    }
}
