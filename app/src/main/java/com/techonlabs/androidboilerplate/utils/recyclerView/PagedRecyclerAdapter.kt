package com.techonlabs.androidboilerplate.utils.recyclerView

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.techonlabs.androidboilerplate.utils.extensions.bindView
import kotlin.reflect.KClass

class PagedRecyclerAdapter(private val viewList: Map<KClass<out ViewType>, Int>,
                           private val listener: OnRecyclerItemClickListener? = null) :
        PagedListAdapter<ViewType, RecyclerVH>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerVH(parent.bindView(viewType))

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    override fun getItemViewType(position: Int) = viewList[(getItem(position) as ViewType)::class]
            ?: throw Exception("View not found for ${(getItem(position) as ViewType)::class}")

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ViewType>() {

            override fun areItemsTheSame(oldItem: ViewType, newItem: ViewType) =
                    oldItem.uniqueId == newItem.uniqueId

            override fun areContentsTheSame(oldItem: ViewType, newItem: ViewType) =
                    oldItem == newItem
        }
    }
}