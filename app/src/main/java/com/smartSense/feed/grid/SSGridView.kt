package com.smartSense.feed.grid

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smartSense.feed.dataModels.FeedImage

/**
 * Created by Mala Ruparel
 */
class SSGridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    private var mStaggeredRecyclerView: RecyclerView? = null
    private var savedData: List<FeedImage>? = null
    private var listener: SSGridItemClickListener? = null
    private var viewMinHeight = 0
    private var viewMaxHeight = 0
    private var viewMaxWidth = 0
    private fun initLayout(context: Context) {
        if (isInEditMode) return
        mStaggeredRecyclerView = RecyclerView(context)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mStaggeredRecyclerView!!.layoutManager = layoutManager
        mStaggeredRecyclerView!!.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(mStaggeredRecyclerView)
    }

    private fun setViewDimensions(minHeightInPercentage: Int, maxHeightInPercentage: Int) {
        if (minHeightInPercentage != 0) viewMinHeight = minHeightInPercentage
        if (maxHeightInPercentage != 0) viewMaxHeight = maxHeightInPercentage
        if (mStaggeredRecyclerView != null) invalidate()
    }

    fun setViewMaxWidthInDP(viewMaxWidth: Int) {
        this.viewMaxWidth = viewMaxWidth
        invalidate()
    }

    fun setData(staggeredData: List<FeedImage>?) {
        if (!isInEditMode && mStaggeredRecyclerView != null) mStaggeredRecyclerView!!.adapter =
            SSGridViewAdapter(
                staggeredData, viewMinHeight, viewMaxHeight, viewMaxWidth, listener
            )
        savedData = staggeredData
    }

    fun setClickListener(listener: SSGridItemClickListener?) {
        this.listener = listener
        if (mStaggeredRecyclerView != null) invalidate()
    }

    override fun invalidate() {
        super.invalidate()
        mStaggeredRecyclerView!!.adapter = SSGridViewAdapter(
            savedData, viewMinHeight, viewMaxHeight, viewMaxWidth, listener
        )
    }

    init {
        setViewDimensions(35, 65)
        initLayout(context)
    }

}