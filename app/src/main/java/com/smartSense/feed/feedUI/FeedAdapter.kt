package com.smartSense.feed.feedUI

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.smartSense.feed.dataModels.FeedData
import com.smartSense.feed.databinding.ItemFeedImageBinding
import com.smartSense.feed.databinding.ItemFeedTextBinding
import com.smartSense.feed.databinding.ItemFeedVideoBinding

import com.smartSense.feed.other.Extensions.Companion.toStringArray


/**
 * Created by Mala Ruparel
 */
class FeedAdapter(var context: Context,private var mFeedList: ArrayList<FeedData>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FEED_TYPE_TEXT = 0
        const val FEED_TYPE_MULTIPLE_IMAGES = 1
        const val FEED_TYPE_VIDEO = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (mFeedList[position].type) {
            0 -> {
                FEED_TYPE_TEXT
            }
            1 -> {
                FEED_TYPE_MULTIPLE_IMAGES
            }
            else -> {
                FEED_TYPE_VIDEO
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            FEED_TYPE_TEXT -> {
                return TextFeedHolder(
                    ItemFeedTextBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            FEED_TYPE_MULTIPLE_IMAGES -> {
                return ImageFeedHolder(
                    ItemFeedImageBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
            FEED_TYPE_VIDEO -> {
                return VideoFeedHolder(
                    ItemFeedVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                return TextFeedHolder(
                    ItemFeedTextBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mFeedList[position]
        when (holder) {
            is TextFeedHolder -> {
                holder.bind(item)
            }
            is ImageFeedHolder -> {
                holder.bind(item)
            }
            is VideoFeedHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class VideoFeedHolder(var binding: ItemFeedVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedData?) {
            /*Reset ViewHolder */
           // binding.feedPlayerView.reset()
            //removeImageFromImageView(binding.feedThumbnailView)


            /*Set seperate ID for each player view, to prevent it being overlapped by other player's changes*/
            binding.feedPlayerView.id = View.generateViewId()

            /*circlular repeatation of items*/
            val videoPos = (adapterPosition % mFeedList.toStringArray().size)

            /*Set ratio according to video*/
            (binding.feedThumbnailView.layoutParams as ConstraintLayout.LayoutParams).dimensionRatio =
                "800:800"

            /*Set video's direct url*/
            binding.feedPlayerView.setVideoUri(Uri.parse(mFeedList.toStringArray()[videoPos]))

            /*Set video's thumbnail locally (by drawable), you can set it by remoteUrl too*/
            val resID: Int = context.resources.getIdentifier(
                "thumbnail_$videoPos",
                "drawable",
                context.packageName
            )

            val res: Drawable? = AppCompatResources.getDrawable(context,resID)
            binding.feedThumbnailView.setImageDrawable(res)
            binding.viewModel = item
            binding.executePendingBindings()
        }
    }

    inner class TextFeedHolder(private var binding: ItemFeedTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedData?) {
            binding.viewModel = item
            binding.executePendingBindings()
        }
    }
    inner class ImageFeedHolder(var binding: ItemFeedImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FeedData?) {
            binding.staggeredGridView.setData(item?.data?.feedImageUri)
            binding.viewModel = item
            binding.executePendingBindings()
        }
    }
    override fun getItemCount(): Int {
        return mFeedList.size
    }
    fun removeImageFromImageView(imageView: ImageView) {
        try {
            imageView.background = null
            imageView.setImageDrawable(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
