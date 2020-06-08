package com.example.testapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.testapp.domain.TopReddit
import com.example.testapp.repository.RedditRepository

class RedditViewModel(application: Application) : AndroidViewModel(application) {

    private val redditRepository = RedditRepository(application)

    fun getTopReddit() : LiveData<TopReddit> =
        redditRepository.mutableLiveData()

}