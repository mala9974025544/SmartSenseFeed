package com.smartSense.feed.grid

import com.smartSense.feed.dataModels.FeedImage

/**
 * Created by Mala Ruparel
 */
class StaggeredDataModel {
    var top: ViewDataModel? = null
    var bottom: ViewDataModel? = null

    class ViewDataModel {
        var heightPercent = 0F
        var staggeredData: FeedImage? = null
    }
}
