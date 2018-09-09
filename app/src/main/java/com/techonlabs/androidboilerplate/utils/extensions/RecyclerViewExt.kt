@file:Suppress("UNCHECKED_CAST")

package com.techonlabs.androidboilerplate.utils.extensions

import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techonlabs.androidboilerplate.utils.recyclerView.RecyclerVH
import com.techonlabs.androidboilerplate.utils.recyclerView.ViewType

fun RecyclerView.addHorizontalDivider(@DrawableRes drawableInt: Int) {
    val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    itemDecor.setDrawable(ContextCompat.getDrawable(context, drawableInt)!!)
    addItemDecoration(itemDecor)
}

fun RecyclerView.getAutoGridLayoutManager(spanCount: Int = 2, viewsWithFullWidth: List<Int>? = null) {
    val gridLayoutManager = GridLayoutManager(context, spanCount)
    if (viewsWithFullWidth != null)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (viewsWithFullWidth.contains(adapter?.getItemViewType(position)) == true) spanCount else 1
            }
        }
    this.layoutManager = gridLayoutManager
}

fun <T : ViewType> PagedListAdapter<ViewType, RecyclerVH>.submitListV2(pagedList: PagedList<T>?) {
    submitList(pagedList as PagedList<ViewType>)
}