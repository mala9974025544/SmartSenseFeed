package com.smartSense.feed.customViewBinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Mala Ruparel
 */
@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>?) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}
