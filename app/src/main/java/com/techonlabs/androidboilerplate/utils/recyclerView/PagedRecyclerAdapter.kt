package com.techonlabs.androidboilerplate.utils.recyclerView

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.techonlabs.androidboilerplate.utils.extensions.bindView
import kotlin.reflect.KClass

class PagedRecyclerAdapter(private val viewList: Map<KClass<out StableId>, Int>,
                           private val listener: OnRecyclerItemClickListener? = null) :
        PagedListAdapter<StableId, RecyclerVH>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerVH(parent.bindView(viewType))

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    override fun getItemViewType(position: Int) = viewList[(getItem(position) as StableId)::class]
            ?: throw Exception("View not found for ${(getItem(position) as StableId)::class}")

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StableId>() {

            override fun areItemsTheSame(oldItem: StableId, newItem: StableId) =
                    oldItem.stableId == newItem.stableId

            override fun areContentsTheSame(oldItem: StableId, newItem: StableId) =
                    oldItem == newItem
        }
    }
}