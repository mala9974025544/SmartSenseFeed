package com.smartSense.feed.dataModels



/**
 * Created by Mala Ruparel
 */
data class User(
    val id: Long = System.currentTimeMillis(),
    val userName: String,
    val userProfile: Int,
    val imgUrl: String,
)





