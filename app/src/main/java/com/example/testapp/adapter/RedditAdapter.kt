package com.example.testapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import kotlin.collections.ArrayList


class RedditAdapter(private val context: Context, redditList: List<RedditChildren>, private val clickListener: (redditChildren: RedditChildrenInformation) -> Unit,
                    private val clickOnDismiss: (redditChildren: Int) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {


    private val redditArrayList: ArrayList<RedditChildren> = redditList as ArrayList<RedditChildren>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        RedditViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.redd_items,
            parent,
            false))

    override fun getItemCount(): Int = redditArrayList.size


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position, redditArrayList[position], context, clickListener, clickOnDismiss)
    }


    fun deleteItem(position: Int) {
        val redditArrayList = redditArrayList
        val recentlyDeletedItem = redditArrayList[position]
        redditArrayList.remove(recentlyDeletedItem)

        notifyItemRemoved(position)
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

        }

        override fun onBind(
            position: Int,
            topReddit: RedditChildren,
            context: Context,
            clickItemListener: (redditChildren: RedditChildrenInformation) -> Unit,
            clickOnDismiss: (position: Int) -> Unit
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

            onItemClick(clickItemListener, topReddit.data)
            onImageClick(topReddit.data.thumbnail, context)
            onDismissClick(clickOnDismiss, position)

        }

        private fun onDismissClick(clickOnDismiss: (position: Int) -> Unit,
                                   position: Int) {
            dismissText.setOnClickListener {
                clickOnDismiss(position)
            }

        }

        private fun onImageClick(thumbnail: String?, context: Context) {
            postImage.setOnClickListener {
              val imageIntent = Intent().apply {
                  action = Intent.ACTION_VIEW
                  addCategory(Intent.CATEGORY_BROWSABLE)
                  flags = Intent.FLAG_ACTIVITY_NEW_TASK
                  data = Uri.parse(thumbnail)
              }
                context.startActivity(imageIntent)
            }
        }

        private fun onItemClick(clickListener: (redditChildren: RedditChildrenInformation) -> Unit,
                                data: RedditChildrenInformation) {
            itemView.setOnClickListener {
                circleView.visibility = View.INVISIBLE
                clickListener(data)
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