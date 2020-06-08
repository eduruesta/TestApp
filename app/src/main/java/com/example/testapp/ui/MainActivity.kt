package com.example.testapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.adapter.RedditAdapter
import com.example.testapp.domain.TopReddit
import com.example.testapp.viewmodels.RedditViewModel
import kotlinx.android.synthetic.main.redd_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var redditAdapter: RedditAdapter
    private lateinit var redditViewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.redd_main)
        redditViewModel = ViewModelProvider(this).get(RedditViewModel::class.java)
        getTopReddit()
    }

    fun getTopReddit() {
        redditViewModel.getTopReddit().observe(this, Observer {
            prepareRecyclerView(it)
        })
    }

    private fun prepareRecyclerView(topRedditList: TopReddit) {
        redditAdapter = RedditAdapter(applicationContext, topRedditList.data.children)

       if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        } else {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        }
        redditRecyclerView.itemAnimator = DefaultItemAnimator()
        redditRecyclerView.adapter = redditAdapter
        redditAdapter.notifyDataSetChanged()
    }
}
