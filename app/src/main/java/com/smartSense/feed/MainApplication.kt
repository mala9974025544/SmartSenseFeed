package com.smartSense.feed

import android.app.Application
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache

import java.io.File

/**
 * Created by Mala Ruparel
 */
class MainApplication : Application() {

    companion object {
        lateinit var cache: SimpleCache
    }

    private val cacheSize: Long = 90 * 1024 * 1024
    private lateinit var cacheEvictor: LeastRecentlyUsedCacheEvictor
    private lateinit var exoplayerDatabaseProvider: StandaloneDatabaseProvider
    override fun onCreate() {
        super.onCreate()
        setUpForPreCaching()
    }


    private fun setUpForPreCaching() {

        cacheEvictor = LeastRecentlyUsedCacheEvictor(cacheSize)
        exoplayerDatabaseProvider = StandaloneDatabaseProvider(this)
        cache = SimpleCache(cacheDir, cacheEvictor, exoplayerDatabaseProvider)


    }

}
