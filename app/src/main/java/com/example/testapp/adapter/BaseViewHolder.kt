package com.example.testapp.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.domain.RedditChildren
import com.example.testapp.domain.RedditChildrenInformation

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected abstract fun clear()

    abstract fun onBind(
        position: Int,
        topReddit: RedditChildren,
        context: Context,
        clickItemListener: (redditChildren: RedditChildrenInformation) -> Unit,
        clickOnDismiss: (position: Int) -> Unit
    )
}