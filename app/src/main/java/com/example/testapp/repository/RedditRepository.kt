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

    var topReddit:  TopReddit? = null
    val mutableRedditLiveData: MutableLiveData<TopReddit> = MutableLiveData()

    val redditLiveData: LiveData<TopReddit>
        get() = mutableRedditLiveData

    fun mutableLiveData(): MutableLiveData<TopReddit> {
        val apiService = RetrofitInstance.getApiService()
        val call = apiService?.getTopReddit()
        call?.enqueue(object: Callback<TopReddit> {

            override fun onFailure(call: Call<TopReddit>, t: Throwable) {
                Log.e(t.message ,"The call is failing")
            }

            override fun onResponse(call: Call<TopReddit>, response: Response<TopReddit>) {
                val topReddit = response.body()
                topReddit.let {
                    this@RedditRepository.topReddit = it
                    mutableRedditLiveData.value = this@RedditRepository.topReddit
                }
            }
        })
        return mutableRedditLiveData
    }
}