package com.smartSense.feed.player

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Looper
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AdViewProvider
import com.google.android.exoplayer2.ui.PlayerView

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource

import com.google.android.exoplayer2.util.Assertions
import com.smartSense.feed.MainApplication.Companion.cache
import com.smartSense.feed.R

/**
 * Created by Mala Ruparel
 */
class FeedPlayerView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? =  /* attrs= */null,
    defStyleAttr: Int =  /* defStyleAttr= */0
) : FrameLayout(
    context!!, attrs, defStyleAttr
), AdViewProvider {

    var exoPlayer: ExoPlayer? = null
    var player: PlayerView? = null

    private var isTouching = false


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (exoPlayer != null) {
            return super.dispatchKeyEvent(event)
        }
        val isDpadKey = isDpadKey(event.keyCode)
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (exoPlayer == null) {
            false
        } else when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouching = true
                true
            }
            MotionEvent.ACTION_UP -> {
                if (isTouching) {
                    isTouching = false
                    performClick()
                    return true
                }
                false
            }
            else -> false
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    override fun onTrackballEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun getAdViewGroup(): ViewGroup? {
        return null
    }


    @SuppressLint("InlinedApi")
    private fun isDpadKey(keyCode: Int): Boolean {
        return keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_UP_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_DOWN_RIGHT || keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_DOWN_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_UP_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_CENTER
    }

    init {

        val playerLayoutId = R.layout.simple_player_view
        LayoutInflater.from(context).inflate(playerLayoutId, this)
        descendantFocusability = FOCUS_AFTER_DESCENDANTS

        // Content frame.
        player = findViewById(R.id.player_view)
        init()
    }


    private var lastPos: Long? = 0
    private var videoUri: Uri? = null

    private var cacheDataSourceFactory = CacheDataSource.Factory()
        .setCache(cache)
        .setUpstreamDataSourceFactory(
            DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)
        )
        .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

    private fun init() {
       // reset()

        /*Setup player + Adding Cache Directory*/
        /* val simpleExoPlayer = ExoPlayer.Builder(context).build()
       // simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == Player.STATE_READY) {
                    simpleExoPlayer.seekTo(lastPos!!)
                    alpha = 1f

                }
            }

        })*/
        exoPlayer = ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).build()


        exoPlayer?.playWhenReady = false
        this.player?.player=exoPlayer


    }

    /**
     * This will resuse the player and will play new URI we have provided
     */
    fun startPlaying() {
        exoPlayer?.playWhenReady = true
        exoPlayer?.seekTo(lastPos!!)
        val mediaItem = videoUri?.let { MediaItem.fromUri(it) }
        val mediaSource =
            mediaItem?.let {
                ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(
                    it
                )
            }

        exoPlayer?.setMediaSource(mediaSource!!,true)
        exoPlayer?.prepare()



    }



    /**
     * This will stop the player, but stopping the player shows blackscreen
     * so to cover that we set alpha to 0 of player
     * and lastFrame of player using imageView over player to make it look like paused player
     *
     * (If we will not stop the player, only pause , then it can cause memory issue due to overload of player
     * and paused player can not be payed with new URL, after stopping the player we can reuse that with new URL
     *
     */
    fun removePlayer() {

        exoPlayer?.playWhenReady = false
        lastPos = exoPlayer?.currentPosition
        //reset()
        exoPlayer?.stop()


    }

    fun reset() {
        // This will prevent surface view to show black screen,
        // and we will make it visible when it will be loaded
        alpha = 0f
    }

    fun setVideoUri(uri: Uri?) {
        this.videoUri = uri
    }
}
