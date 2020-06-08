package com.example.testapp.domain

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TopReddit(val kind: String, val data: RedditInformation) : Serializable

data class RedditInformation(val modhash: String?, val children: List<RedditChildren>,
                             val after: String?, val before: String?) : Serializable

data class RedditChildren(val kind: String, val data: RedditChildrenInformation): Serializable

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RedditChildrenInformation(val author:String?, val thumbnail: String?, val title: String?,

                                     @SerializedName("num_comments") val comments: Int, val clicked: Boolean,
                                     val created: Int) : Serializable
