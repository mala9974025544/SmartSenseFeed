package com.smartSense.feed.other

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.smartSense.feed.dataModels.FeedData

/**
 * Created by Mala Ruparel
 */
class Extensions {

    companion object {

        fun RecyclerView.findFirstVisibleItemPosition(): Int {
            if (layoutManager is LinearLayoutManager) {
                return (layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            }
            return if (layoutManager is StaggeredGridLayoutManager) {
                val mItemPositionsHolder =
                    IntArray((layoutManager as StaggeredGridLayoutManager?)!!.spanCount)
                return min(
                    (layoutManager as StaggeredGridLayoutManager?)!!.findFirstVisibleItemPositions(
                        mItemPositionsHolder
                    )
                )
            } else -1
        }


        fun max(array: IntArray): Int {

            // Finds and returns max
            var max: Int = array[0]
            for (j in 1 until array.size) {
                if (array[j] > max) {
                    max = array[j]
                }
            }
            return max
        }



        fun min(array: IntArray): Int {

            // Finds and returns max
            var min: Int = array[0]
            for (j in 1 until array.size) {
                if (array[j] < min) {
                    min = array[j]
                }
            }
            return min
        }


        fun RecyclerView.findLastVisibleItemPosition(): Int {
            if (layoutManager is LinearLayoutManager) {
                return (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
            return if (layoutManager is StaggeredGridLayoutManager) {
                findLastVisibleItemPosition(layoutManager as StaggeredGridLayoutManager)
            } else -1
        }


        fun findLastVisibleItemPosition(
            staggeredGridLayoutManager: StaggeredGridLayoutManager
        ): Int {
            val mItemPositionsHolder = IntArray(staggeredGridLayoutManager.spanCount)
            return max(
                staggeredGridLayoutManager.findLastVisibleItemPositions(mItemPositionsHolder)
            )
        }


        fun RecyclerView.isAtTop(): Boolean {
            val pos: Int = (layoutManager as LinearLayoutManager?)?.findFirstCompletelyVisibleItemPosition()!!
            return pos == 0
        }


        fun Context.getResource(name:String): Drawable? {
            val resID = this.resources.getIdentifier(name , "drawable", this.packageName)
            return ActivityCompat.getDrawable(this,resID)
        }
        fun <E> java.util.ArrayList<E>.toStringArray(): java.util.ArrayList<String> {

            val arr = java.util.ArrayList<String>()
            forEach {
                (it as FeedData).data?.feedVideo?.let { it1 -> arr.add(it1) }
            }
            return arr

        }
    }

}