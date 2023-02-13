package com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface RetrofitAPI {
    @Headers("Content-Type: application/json")
    @GET
    Call<RecyclerData> getCourse(@Url String url);
}
