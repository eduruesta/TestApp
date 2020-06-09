package com.example.testapp.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.domain.RedditChildren
import com.example.testapp.domain.RedditChildrenInformation
import kotlinx.android.synthetic.main.redd_items.view.*
import java.text.ParseException
import java.util.*


class RedditAdapter(private val context: Context, private val redditList: List<RedditChildren>, private val clickListener: (redditChildren: RedditChildrenInformation) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        RedditViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.redd_items,
            parent,
            false
        ))

    override fun getItemCount(): Int = redditList.size


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, redditList[position], context, clickListener)
    }




    class RedditViewHolder(itemView: View) :BaseViewHolder(itemView) {

        private val circleView: ImageView = itemView.circleView
        private val postTitle: TextView = itemView.postTitle
        private val postCreated: TextView = itemView.postCreated
        private val postImage: ImageView = itemView.postImage
        private val postDescription: TextView = itemView.postDescription
        private val dismissText: TextView = itemView.dismissText
        private val commentsNumber: TextView = itemView.commentsNumber

        override fun clear() {
            TODO("Not yet implemented")
        }

        override fun onBind(
            position: Int,
            topReddit: RedditChildren,
            context: Context,
            clickListener: (redditChildren: RedditChildrenInformation) -> Unit
        ) {
           topReddit.data.let {
               postTitle.text = it.author
               postDescription.text = it.title
               commentsNumber.text = it.comments.toString().plus(" comments")
               Glide.with(itemView.context)
                   .load(it.thumbnail)
                   .into(postImage)
                postCreated.text = entryDate(it.created)
           }

            itemView.setOnClickListener {
                circleView.visibility = View.INVISIBLE
                clickListener(topReddit.data)
            }
        }

        private fun entryDate(created: Int): CharSequence? {
            val date = Date(created * 1000L).toInstant()
            try {
                val now = System.currentTimeMillis()
                return DateUtils.getRelativeTimeSpanString(date.toEpochMilli(), now, DateUtils.MINUTE_IN_MILLIS)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }
    }
}