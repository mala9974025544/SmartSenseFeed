package com.smartSense.feed.autoPlay

import android.graphics.Rect
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartSense.feed.feedUI.FeedAdapter
import com.smartSense.feed.player.FeedPlayerView
import kotlin.math.max
import kotlin.math.min

/**
 * Create By Mala Ruparel
 */
class FeedVideoAutoPlayHelper(var recyclerView: RecyclerView) {

    private var lastPlayerView: FeedPlayerView? = null
    private val minLimitVisibility =
        20 // When playerView will be less than 20% visible than it will stop the player

    private var currentPlayingVideoItemPos = -1 // -1 indicates nothing playing

    fun onScrolled(recyclerView: RecyclerView, feedAdapter: FeedAdapter) {

        val firstVisiblePosition: Int = findFirstVisibleItemPosition()
        val lastVisiblePosition: Int = findLastVisibleItemPosition()

        val pos = getMostVisibleItem(firstVisiblePosition, lastVisiblePosition)

        if (pos == -1) {
            /*check if current view is more than MIN_LIMIT_VISIBILITY*/
            if (currentPlayingVideoItemPos != -1) {
                val viewHolder: RecyclerView.ViewHolder =
                    recyclerView.findViewHolderForAdapterPosition(currentPlayingVideoItemPos)!!
                val currentVisibility = getVisiblePercentage(viewHolder)
                if (currentVisibility < minLimitVisibility) {
                    lastPlayerView?.removePlayer()
                }
                currentPlayingVideoItemPos = -1
            }

        } else {

            if (currentPlayingVideoItemPos != pos) {
                currentPlayingVideoItemPos = pos
                attachVideoPlayerAt(pos,feedAdapter)
            }

        }

    }

    private fun attachVideoPlayerAt(pos: Int,feedAdapter: FeedAdapter) {
        if(feedAdapter.getItemViewType(pos)==2) {
            val feedViewHolder: FeedAdapter.VideoFeedHolder =
                (recyclerView.findViewHolderForAdapterPosition(pos) as FeedAdapter.VideoFeedHolder?)!!
            /** in case its a video**/
            if (lastPlayerView==null || lastPlayerView != feedViewHolder.binding.feedPlayerView) {
                feedViewHolder.binding.feedPlayerView.startPlaying()
                // stop last player
                lastPlayerView?.removePlayer()
            }
            lastPlayerView = feedViewHolder.binding.feedPlayerView

        } else {
            /** in case its a image**/
            if (lastPlayerView != null) {
                // stop last player
                lastPlayerView?.removePlayer()
                lastPlayerView = null
            }

        }
    }
    private fun getMostVisibleItem(firstVisiblePosition: Int, lastVisiblePosition: Int): Int {

        var maxPercentage = -1
        var pos = 0
        for (i in firstVisiblePosition..lastVisiblePosition) {
            val viewHolder: RecyclerView.ViewHolder =
                recyclerView.findViewHolderForAdapterPosition(i)!!

            val currentPercentage = getVisiblePercentage(viewHolder)
            if (currentPercentage > maxPercentage) {
                maxPercentage = currentPercentage.toInt()
                pos = i
            }

        }

        if (maxPercentage == -1 || maxPercentage < minLimitVisibility) {
            return -1
        }

        return pos
    }

    private fun getVisiblePercentage(
        holder: RecyclerView.ViewHolder
    ): Float {
        val rectParent = Rect()
        recyclerView.getGlobalVisibleRect(rectParent)
        val location = IntArray(2)
        holder.itemView.getLocationOnScreen(location)

        val rectChild = Rect(
            location[0],
            location[1],
            location[0] + holder.itemView.width,
            location[1] + holder.itemView.height
        )

        val rectParentArea =
            ((rectChild.right - rectChild.left) * (rectChild.bottom - rectChild.top)).toFloat()
        val xOverlap = max(
            0,
            min(rectChild.right, rectParent.right) - max(
                rectChild.left,
                rectParent.left
            )
        ).toFloat()
        val yOverlap = max(
            0,
            min(rectChild.bottom, rectParent.bottom) - max(
                rectChild.top,
                rectParent.top
            )
        ).toFloat()
        val overlapArea = xOverlap * yOverlap

        return overlapArea / rectParentArea * 100.0f
    }

    private fun findFirstVisibleItemPosition(): Int {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            return (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
        }

        return -1
    }

    private fun findLastVisibleItemPosition(): Int {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            return (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }
        return -1
    }

    fun startObserving() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.adapter is FeedAdapter) {
                    onScrolled(recyclerView, recyclerView.adapter as FeedAdapter)
                } else {
                    throw IllegalStateException("Adapter should be FeedAdapter or extend FeedAdapter")
                }
            }
        })
    }
    fun stopObserving() {
        lastPlayerView?.removePlayer()

    }

}