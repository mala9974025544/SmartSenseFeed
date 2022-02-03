package com.smartSense.feed.dataModels
/**
 * Created by Mala Ruparel
 */
data class Feed(var `data`: ArrayList<FeedData>? = null,)

data class FeedData(
    var id: Long = System.currentTimeMillis(),
    var type: Int? = null,
    var data: Data? = null,
    )

data class Data(
    var authorName: String? = null,
    var authorPhoto: Int? = null,
    var feedText: String? = null,
    var timestamp: Long = System.currentTimeMillis(),
    var comments: Comments? = null,
    var description: String? = null,
    var likes: Likes? = null,
    var feedImageUri: ArrayList<FeedImage>? = null,
    var dimension: String? = null,
    var feedVideo: String? = null,
)

data class FeedImage(
    var imageUri: String? = null
)

data class Comments(
    var count: Int,
    var `data`: List<CommentsData>
)

data class CommentsData(
    var created_time: String,
    var from: From,
    var id: String,
    var message: String
)

data class From(
    var id: String,
    var name: String
)


data class Likes(
    var count: Int,
    var `data`: List<LikesData>
)

data class LikesData(
    var category: String,
    var id: String,
    var name: String
)






