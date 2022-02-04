package com.smartSense.feed.grid

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.smartSense.feed.R
import com.smartSense.feed.dataModels.FeedImage

import java.util.*
import kotlin.math.roundToInt

/**
 * Created by Mala Ruparel
 */
class SSGridViewAdapter(
    staggeredData: List<FeedImage?>?, minHeight: Int,
    maxHeight: Int, maxWidth: Int, listener: SSGridItemClickListener?
) :
    RecyclerView.Adapter<SSGridViewAdapter.StaggeredViewHolder?>() {
    private val staggeredDataModelList: MutableList<StaggeredDataModel> =
        ArrayList<StaggeredDataModel>()
    private var minHeight: Int
    private var maxHeight: Int
    private var maxWidth: Int
    private val listener: SSGridItemClickListener?
    fun setMinHeight(minHeight: Int) {
        this.minHeight = minHeight
    }

    fun setMaxHeight(maxHeight: Int) {
        this.maxHeight = maxHeight
    }

    fun setMaxWidth(maxWidth: Int) {
        this.maxWidth = maxWidth
    }

    private fun getItemAt(pos: Int): StaggeredDataModel {
        return staggeredDataModelList[pos]
    }


    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): StaggeredViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_staggered, parent, false)
        val display = (parent.context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        if (maxWidth == 0) {
            val deviceDisplay = Point()
            display.getSize(deviceDisplay)
            val deviceWidth = deviceDisplay.x
            maxWidth = pxToDp((deviceWidth / itemCount).toFloat())
        }
        if (maxWidth > 120) {
            view.layoutParams = FrameLayout.LayoutParams(
                dpToPx(maxWidth.toFloat()), ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return StaggeredViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: StaggeredViewHolder, position: Int) {
        val staggeredDataModel: StaggeredDataModel = getItemAt(position)
        if (staggeredDataModel.top != null) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0,
                staggeredDataModel.top!!.heightPercent
            )
            holder.topViewContainer.layoutParams = params
            //holder.topTitleView.text = "4+"
            Glide.with(holder.topImageView.context)
                .load(staggeredDataModel.top!!.staggeredData?.imageUri)
                .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).diskCacheStrategy(
                    DiskCacheStrategy.DATA))
                // .fitCenter() )
                //.skipMemoryCache(true))
                .into(holder.topImageView)
            holder.topViewContainer.setOnClickListener {
                listener?.onStaggeredItemClick(
                    staggeredDataModel.top!!.staggeredData?.imageUri.toString()
                )
            }
        }
        if (staggeredDataModel.bottom != null) {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0,
                staggeredDataModel.bottom!!.heightPercent
            )
            holder.bottomViewContainer.layoutParams = params
            holder.bottomTitleView.text = "+4"
            Glide.with(holder.bottomImageView.context)
                .load(staggeredDataModel.bottom!!.staggeredData!!.imageUri)
                .apply(RequestOptions().placeholder(R.drawable.ic_placeholder).diskCacheStrategy(
                    DiskCacheStrategy.DATA))
                // .fitCenter() )
                //.skipMemoryCache(true))
                .into(holder.bottomImageView)
            holder.bottomViewContainer.setOnClickListener {
                listener?.onStaggeredItemClick(
                    staggeredDataModel.bottom!!.staggeredData.toString()
                )
            }
        } else {
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            holder.topViewContainer.layoutParams = params
        }
        if (staggeredDataModelList.size == 2 && position == 1) {
            holder.bottomVIew.visibility = View.VISIBLE
        } else {
            holder.bottomVIew.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return staggeredDataModelList.size
    }



    private val randomHeight: Float
        private get() {
            val r = Random()
            return (r.nextInt(maxHeight - minHeight + 1) + minHeight).toFloat()
        }

    private fun dpToPx(dp: Float): Int {
        return (dp * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / 160.0f)).roundToInt()
    }

    private fun pxToDp(px: Float): Int {
        return (px / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / 160.0f)).roundToInt()
    }

    init {
       // if (staggeredData == null || staggeredData.isEmpty())
        //    return
        this.minHeight = minHeight
        this.maxHeight = maxHeight
        this.maxWidth = maxWidth
        this.listener = listener
        var maxPos = staggeredData!!.size
        if (maxPos > 3) {
            maxPos = 4
        }
        var pos = 0
        while (pos < maxPos) {
            val staggeredDataModel = StaggeredDataModel()
            val topViewData: StaggeredDataModel.ViewDataModel = StaggeredDataModel.ViewDataModel()
            topViewData.staggeredData=staggeredData[pos]
            topViewData.heightPercent=randomHeight
            staggeredDataModel.top=topViewData
            if (pos < 3 && maxPos>2) {
                try {
                    val bottomViewData: StaggeredDataModel.ViewDataModel =
                        StaggeredDataModel.ViewDataModel()
                    bottomViewData.staggeredData=(staggeredData[pos + 1])
                    bottomViewData.heightPercent=(100 - topViewData.heightPercent)
                    staggeredDataModel.bottom=(bottomViewData)
                } catch (e: Exception) {
                    Log.d("StaggeredViewAdapter", "Bottom none, display top at max height")
                }
                pos++
           }
            staggeredDataModelList.add(staggeredDataModel)
            pos++
        }
    }
    inner class StaggeredViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var topTitleView: TextView = itemView.findViewById(R.id.topTitleView)
        var bottomTitleView: TextView
        var topImageView: ImageView
        var bottomImageView: ImageView
        var topViewContainer: RelativeLayout
        var bottomViewContainer: RelativeLayout
        var bottomVIew: LinearLayout

        init {
            bottomTitleView = itemView.findViewById(R.id.bottomTitleView)
            topImageView = itemView.findViewById(R.id.topImageView)
            bottomImageView = itemView.findViewById(R.id.bottomImageView)
            topViewContainer = itemView.findViewById(R.id.topViewContainer)
            bottomViewContainer = itemView.findViewById(R.id.bottomViewContainer)
            bottomVIew = itemView.findViewById(R.id.bottomView)
        }
    }
}
