package com.koreait.pictest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/v2/list")
    Call<List<picsumVO>> getList();
}
