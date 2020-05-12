package com.aissoft.wfh;
public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://10.1.211.149:8888/api/";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}