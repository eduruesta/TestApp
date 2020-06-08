package com.example.testapp.retrofit

import com.example.testapp.domain.TopReddit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("/top.json?count=50")
    fun getTopReddit(): Call<TopReddit>
}