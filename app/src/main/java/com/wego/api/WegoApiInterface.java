package com.wego.api;

import com.wego.model.pojo.CategoriesResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WegoApiInterface {

    @GET("nearbysearch/json")
    Call<CategoriesResults> getCategories(@Query("location") String location, @Query("radius") String radius,
                                          @Query("type") String type, @Query("key") String apiKey);
}