/*
package com.smartSense.feed.autoPlay

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import com.smartSense.feed.R
import com.smartSense.feed.other.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

*/
/**
 * Create By Mala Ruparel
 *//*

*/
/**
 *
 * This class provides way to precache the video from remoteUrl in to Cache Directory
 * So Player will not reload videos from server if they are already loaded in cache
 *//*

class FeedVideoPreLoadingService : IntentService(FeedVideoPreLoadingService::class.java.simpleName) {
    private lateinit var mContext: Context
    private var simpleCache: SimpleCache? = null
    private var cachingJob: Job? = null
    private var videosList: ArrayList<String>? = null

    override fun onHandleIntent(intent: Intent?) {
        mContext = applicationContext

        if (intent != null) {
            val extras = intent.extras
            videosList = extras?.getStringArrayList(Constants.VIDEO_LIST)

            if (!videosList.isNullOrEmpty()) {
                preCacheVideo(videosList)
            }
        }
    }

    private fun preCacheVideo(videosList: ArrayList<String>?) {
        var videoUrl: String? = null
        if (!videosList.isNullOrEmpty()) {
            videoUrl = videosList[0]
            videosList.removeAt(0)
        } else {
            stopSelf()
        }
        if (!videoUrl.isNullOrBlank()) {
            val videoUri = Uri.parse(videoUrl)
            val dataSpec = DataSpec(videoUri)


            val progressListener =
                CacheWriter.ProgressListener { requestLength, bytesCached, newBytesCached ->
                    val downloadPercentage: Double = (bytesCached * 100.0
                            / requestLength)

                    Log.v("VideoPreLoadingService", "downloadPercentage url$videoUrl")
                    Log.v("VideoPreLoadingService", "downloadPercentage % = $downloadPercentage")
                }
            val dataSource: DataSource =
                DefaultDataSourceFactory(
                    mContext,
                    Util.getUserAgent(this, getString(R.string.app_name))
                ).createDataSource()

            cachingJob = GlobalScope.async(Dispatchers.IO) {
                cacheVideo(dataSpec,  dataSource, progressListener)
                preCacheVideo(videosList)
            }
            cachingJob?.start()
        }
    }

    private fun cacheVideo(
        dataSpec: DataSpec,
        defaultCacheKeyFactory: CacheKeyFactory?,
        dataSource: CacheDataSource,
        progressListener: CacheWriter.ProgressListener
    ) {
        try {
            CacheWriter(
                dataSource,
                dataSpec,

                CacheWriter.DEFAULT_BUFFER_SIZE_BYTES[],

                progressListener,

            )
        } catch (e: java.io.EOFException) {

        } catch (e: Exception) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cachingJob?.cancel()

    }
}*/
