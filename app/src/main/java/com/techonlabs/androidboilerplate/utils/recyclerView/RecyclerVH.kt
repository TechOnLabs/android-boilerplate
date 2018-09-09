package com.techonlabs.androidboilerplate.utils.recyclerView

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView


open class RecyclerVH(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    open fun bind(obj: StableId, listenerRecycler: OnRecyclerItemClickListener?) {
        binding.setVariable(BR.obj, obj)
        binding.setVariable(BR.lis, listenerRecycler)
        binding.executePendingBindings()
        itemView.setOnClickListener { listenerRecycler?.onRecyclerItemClick(obj) }
    }
}


//Extend data classes with StableId
interface StableId {
    /** Used to check if the item is changed or not in the list. It should be unique for every cell in list. It is used by DiffCallback*/
    val stableId: String
}

interface OnRecyclerItemClickListener {
    fun onRecyclerItemClick(obj: StableId) {}
}
