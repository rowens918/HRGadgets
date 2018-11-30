package com.tbsfactoringapp.hrgadgets;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String KEY_EMP_ID = "id";
    private static final String KEY_EMP_NAME = "employee_name";
    private static final String KEY_EMP_SALARY = "employee_salary";
    private static final String KEY_EMP_AGE = "employee_age";
    private static final String KEY_EMP_IMAGE = "profile_image";


    public static List<EmployeeInfo> parseEmployeeData(String rawJson) {
        List<EmployeeInfo> employees = new ArrayList<>();
        try {
            JSONArray results = new JSONArray(rawJson);


            for (int i = 0; i < results.length(); i++) {
                EmployeeInfo tempEmp = new EmployeeInfo();
                tempEmp.mEmpId = results.getJSONObject(i).optString(KEY_EMP_ID);
                tempEmp.mEmpName = results.getJSONObject(i).optString(KEY_EMP_NAME);
                tempEmp.mEmpSalary = results.getJSONObject(i).optString(KEY_EMP_SALARY);
                tempEmp.mEmpAge = results.getJSONObject(i).optString(KEY_EMP_AGE);
                tempEmp.mEmpImage = results.getJSONObject(i).optString(KEY_EMP_IMAGE);
                employees.add(i, tempEmp);
            }
            return employees;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EmployeeInfo parseSingleEmployeeData(String rawJson) {
        if (rawJson.startsWith("{")) {
            EmployeeInfo tempEmp = new EmployeeInfo();
            try {
                JSONObject result = new JSONObject(rawJson);
                tempEmp.mEmpId = result.optString(KEY_EMP_ID);
                tempEmp.mEmpName = result.optString(KEY_EMP_NAME);
                tempEmp.mEmpSalary = result.optString(KEY_EMP_SALARY);
                tempEmp.mEmpAge = result.optString(KEY_EMP_AGE);
                tempEmp.mEmpImage = result.optString(KEY_EMP_IMAGE);
                return tempEmp;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}