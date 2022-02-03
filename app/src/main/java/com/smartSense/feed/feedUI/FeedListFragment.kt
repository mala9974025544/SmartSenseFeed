package com.smartSense.feed.feedUI

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.smartSense.feed.other.Extensions.Companion.findFirstVisibleItemPosition
import com.smartSense.feed.other.Extensions.Companion.isAtTop
import com.smartSense.feed.autoPlay.FeedVideoAutoPlayHelper
import com.smartSense.feed.autoPlay.VideoPreloadWorker
import com.smartSense.feed.dataModels.Feed
import com.smartSense.feed.dataModels.FeedData
import com.smartSense.feed.dataModels.FeedViewModel
import com.smartSense.feed.databinding.FragmentFeedListBinding
import com.smartSense.feed.other.Extensions.Companion.toStringArray

/**
 * Created by Mala Ruparel
 */
class FeedListFragment : Fragment() {

    private var controlsVisibleShowHide: Boolean = false
    private val HIDE_THRESHOLD = 100
    private var isHeaderAlreadyHidden = false
    private lateinit var binding: FragmentFeedListBinding
    private var scrolledDistance: Int = 0
    private lateinit var  videoAutoPlayHelper : FeedVideoAutoPlayHelper
    private lateinit var viewModel: FeedViewModel
    private lateinit var mFeedData: Feed
    private var mFeedList: ArrayList<FeedData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        getFeedData()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("onActivityCreated","onActivityCreated")
        /* Set adapter (items are being used inside adapter, you can setup in your own way*/

        mFeedList.clear()
        mFeedData.data?.let { mFeedList.addAll(it) }

        val feedAdapter = FeedAdapter(requireContext(),mFeedList)
        binding.adapter = feedAdapter

        /*Helper class to provide AutoPlay feature inside cell*/
        videoAutoPlayHelper =
            FeedVideoAutoPlayHelper(recyclerView = binding.rvFeedList)
        videoAutoPlayHelper.startObserving()

        /*Helper class to provide show/hide toolBar*/
        attachScrollControlListener(binding.feedToolbar, binding.rvFeedList)
    }
    private fun getFeedData() {
        mFeedData = viewModel.getPosts()

        startPreCachingOfVideo()
    }

    /**
     * This method will show hide view passed as @param -toolBar
     */
    private fun attachScrollControlListener(toolBar: View?, recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                var firstVisibleItem = -1
                try {
                    firstVisibleItem = recyclerView.findFirstVisibleItemPosition()
                } catch (e: Exception) {

                }

                if (firstVisibleItem == -1) {
                    return
                }

                //show views if first item is first visible position and views are hidden

                if (firstVisibleItem == 0 && recyclerView.computeVerticalScrollOffset() < HIDE_THRESHOLD) {
                    if (!controlsVisibleShowHide) {
                        controlsVisibleShowHide = true
                        showTopBarWithAnim(toolBar, recyclerView, true, null)
                        scrolledDistance = 0
                    }
                } else {
                    if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisibleShowHide) {
                        controlsVisibleShowHide = true
                        showTopBarWithAnim(toolBar, recyclerView, true, null)
                        scrolledDistance = 0
                    } else if (dy > 0/* && hideForcefully()*/ || scrolledDistance > HIDE_THRESHOLD && controlsVisibleShowHide) {
                        controlsVisibleShowHide = false
                        showTopBarWithAnim(toolBar, recyclerView, false, null)
                        scrolledDistance = 0
                    }
                }

                if (controlsVisibleShowHide && dy > 0 || !controlsVisibleShowHide && dy < 0) {
                    scrolledDistance += dy
                }

            }
        })

    }

    /***
     * Animation to show/hide
     */
    fun showTopBarWithAnim(
        toolBar: View?,
        recyclerView: RecyclerView,
        show: Boolean,
        animationListener: Animator.AnimatorListener?
    ) {
        if (show) {
            if (!isHeaderAlreadyHidden) {
                return
            }
            isHeaderAlreadyHidden = false
            toolBar?.animate()?.translationY(0f)?.interpolator = DecelerateInterpolator(2f)
        } else {
            // To check if at the top of recycler view
            if (recyclerView.isAtTop()
            ) {
                // Its at top
                return
            }
            if (isHeaderAlreadyHidden) {
                return
            }
            isHeaderAlreadyHidden = true
            toolBar?.animate()
                ?.translationY((-toolBar.getHeight()!!).toFloat())?.interpolator =
                AccelerateInterpolator(2F)

        }
    }

    override fun onStop() {
        super.onStop()
        videoAutoPlayHelper.stopObserving()
        Log.d("FeedListFragment","onStop")
    }


    override fun onStart() {
        super.onStart()
        videoAutoPlayHelper.startObserving()
        Log.d("FeedListFragment","onStart")
    }
    private fun startPreCachingOfVideo() {


            if(mFeedList.toStringArray().size>1) {

                val workManager = context?.applicationContext?.let { WorkManager.getInstance(it) }
                val videoPreloadWorker =
                    VideoPreloadWorker.buildWorkRequest(mFeedList[0].data?.feedVideo.toString())
                workManager?.enqueueUniqueWork(
                    "VideoPreloadWorker",
                    ExistingWorkPolicy.KEEP,
                    videoPreloadWorker
                )


            }



    }
}
