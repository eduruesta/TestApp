package com.example.testapp.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.adapter.RedditAdapter
import com.example.testapp.domain.RedditChildren
import com.example.testapp.domain.RedditChildrenInformation
import com.example.testapp.domain.TopReddit
import com.example.testapp.viewmodels.RedditViewModel
import kotlinx.android.synthetic.main.redd_items_info.*
import kotlinx.android.synthetic.main.redd_items_info.view.*
import kotlinx.android.synthetic.main.redd_items_info.view.imageInfo
import kotlinx.android.synthetic.main.redd_items_info.view.subtitleInfo
import kotlinx.android.synthetic.main.redd_items_info.view.titleInfo
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
        redditAdapter = RedditAdapter(applicationContext, topRedditList.data.children) { item -> doClick(item)}

        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        } else {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        }
        redditRecyclerView.itemAnimator = DefaultItemAnimator()
        redditRecyclerView.adapter = redditAdapter
        redditAdapter.notifyDataSetChanged()
    }

    private fun doClick(item: RedditChildrenInformation) {
        titleInfo.text = item.author
        Glide.with(applicationContext)
            .load(item.thumbnail)
            .into(imageInfo)
        subtitleInfo.text = item.title
    }

}


