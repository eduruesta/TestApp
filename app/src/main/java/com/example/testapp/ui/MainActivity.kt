package com.example.testapp.ui

import android.Manifest.permission
import android.animation.ObjectAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
import kotlinx.android.synthetic.main.redd_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var redditAdapter: RedditAdapter
    private lateinit var redditViewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.redd_main)
        redditViewModel = ViewModelProvider(this).get(RedditViewModel::class.java)
        getTopReddit()
        swiperefresh.setOnRefreshListener {
            getTopReddit()
        }
        separator.setOnTouchListener(LeftLayoutTouchListener(swiperefresh))
    }

    private fun getTopReddit() {
        swiperefresh.isRefreshing = true
        redditViewModel.getTopReddit().observe(this, Observer {
            swiperefresh.isRefreshing = false
            prepareRecyclerView(it)
        })
    }


    private fun prepareRecyclerView(topRedditList: TopReddit) {
        val redditChildren = ArrayList<RedditChildren>()
        for (redditChidr in topRedditList.data.children) {
            redditChildren.add(redditChidr)
        }
        redditAdapter = RedditAdapter(applicationContext, redditChildren, { item -> clickOnImage(item) },
            { position -> clickOnDismiss(position)})

        configureRecyclerView()
        onDismissAll(redditChildren)
    }

    //Configure Recycler
    private fun configureRecyclerView() {
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        } else {
            redditRecyclerView.layoutManager = (LinearLayoutManager(this))
        }
        redditRecyclerView.itemAnimator = DefaultItemAnimator()
        redditRecyclerView.adapter = redditAdapter
        redditAdapter.notifyDataSetChanged()
    }


    private fun onDismissAll(redditChildren: ArrayList<RedditChildren>) {
        dismissButton.setOnClickListener {
            redditChildren.clear()
            redditAdapter.notifyDataSetChanged()
        }
    }

    private fun clickOnImage(item: RedditChildrenInformation) {
        titleInfo.text = item.author
        Glide.with(applicationContext)
            .load(item.thumbnail)
            .into(imageInfo)
        subtitleInfo.text = item.title
        imageInfo.setOnClickListener {
            if (isWriteStoragePermissionGranted()) {
                saveImageToGallery(imageInfo, item.title)
            }
        }
    }

     private fun clickOnDismiss(position: Int) {
         redditAdapter.deleteItem(position)
         redditAdapter.notifyItemChanged(position)
     }

    private fun saveImageToGallery(imageInfo: ImageView?, title: String?) {
        val drawable = imageInfo?.drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        // Save image to gallery
        val saveImageUrl = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            title,
            "Image of $title"
        )
        toast("saved : $saveImageUrl")
    }


    private fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isWriteStoragePermissionGranted() = if (Build.VERSION.SDK_INT >= 26) {
        if (checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                2
            )
            false
        }
    } else {
        true
    }
}




