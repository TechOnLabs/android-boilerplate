package com.techonlabs.androidboilerplate.utils.recyclerView

import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.techonlabs.androidboilerplate.R


open class RecyclerVH(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    /**NOTE use only obj, lis as variable names inside xml files
     * obj: it is the class linked to the view
     * lis: it is the listener added to the view*/
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
    fun getStableId(): String
}

interface OnRecyclerItemClickListener {
    fun onRecyclerItemClick(obj: StableId) {}
}


/** Empty States*/
data class EmptyListModel(
        @StringRes val textId: Int = R.string.app_name
) : StableId {
    override fun getStableId() = textId.toString()
}