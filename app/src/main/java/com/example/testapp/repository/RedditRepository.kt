package com.example.testapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testapp.domain.TopReddit
import retrofit2.Callback
import com.example.testapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response

class RedditRepository(var application: Application) {

    var topReddit: List<TopReddit> = mutableListOf()
    val mutableRedditLiveData: MutableLiveData<List<TopReddit>> = MutableLiveData()

    val redditLiveData: LiveData<List<TopReddit>>
        get() = mutableRedditLiveData

    fun mutableLiveData(): MutableLiveData<List<TopReddit>> {
        val apiService = RetrofitInstance.getApiService()
        val call = apiService?.getTopReddit()
        call?.enqueue(object: Callback<List<TopReddit>> {

            override fun onFailure(call: Call<List<TopReddit>>, t: Throwable) {
                Log.e(t.message ,"The call is failing")
            }

            override fun onResponse(call: Call<List<TopReddit>>, response: Response<List<TopReddit>>) {
                val topReddit = response.body()
                topReddit.let {
                    this@RedditRepository.topReddit = it as List<TopReddit>
                    mutableRedditLiveData.value = this@RedditRepository.topReddit
                }
            }
        })
        return mutableRedditLiveData
    }
}