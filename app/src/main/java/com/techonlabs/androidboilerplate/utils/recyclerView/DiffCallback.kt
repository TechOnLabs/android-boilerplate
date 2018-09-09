package com.techonlabs.androidboilerplate.utils.recyclerView

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil


class DiffCallback(private val oldList: List<StableId>, private val newList: List<StableId>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].stableId == newList[newItemPosition].stableId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]


    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int) =
            super.getChangePayload(oldItemPosition, newItemPosition)

}
