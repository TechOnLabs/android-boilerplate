package com.techonlabs.androidboilerplate.utils.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.techonlabs.androidboilerplate.R
import com.techonlabs.androidboilerplate.utils.extensions.bindView
import timber.log.Timber
import kotlin.reflect.KClass

open class RecyclerAdapter(private val viewList: MutableMap<KClass<out StableId>, Int>,
                           private val listener: OnRecyclerItemClickListener? = null) : RecyclerView.Adapter<RecyclerVH>() {

    init {
        viewList[EmptyListModel::class] = R.layout.empty_list_cell
    }

    protected var items = mutableListOf<StableId>()
    private var emptyListCell: StableId = EmptyListModel()


    override fun getItemViewType(position: Int) = viewList[getObjForPosition(position)::class]
            ?: throw Exception("View not found for ${getObjForPosition(position)::class}")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerVH(parent.bindView(viewType))

    override fun onBindViewHolder(holder: RecyclerVH, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        getObjForPosition(position).let { holder.bind(it, listener) }
    }

    override fun onBindViewHolder(holder: RecyclerVH, position: Int) {
        getObjForPosition(position).let { holder.bind(it, listener) }
    }

    override fun getItemCount() = items.size

    fun getObjForPosition(position: Int) = items[position]


    fun getPositionForObj(obj: StableId) = items.indexOf(obj)

    fun addItems(items: List<StableId>?) {
        items?.let {
            val initSize = itemCount
            this.items.addAll(items)
            notifyItemRangeInserted(initSize, items.size)
        }
    }

    fun addItem(item: StableId?, pos: Int = itemCount) {
        if (item != null && pos >= 0) {
            items.add(pos, item)
            notifyItemInserted(pos)
        }
    }

    fun updateItem(prevItem: StableId?, newItem: StableId?, notify: Boolean = true) {
        if (prevItem != null && newItem != null) {
            val index = items.indexOf(prevItem)
            if (index != -1) {
                items[index] = newItem
                if (notify)
                    notifyItemChanged(index)
            }
        }
    }

    fun updateItemAt(item: StableId?, pos: Int) {
        if (item != null && pos in 0 until itemCount) {
            items[pos] = item
            notifyItemChanged(pos)
        }
    }

    fun nukeList() {
        clearAllItems()
        showEmptyState()
    }

    fun removeItem(item: StableId?) {
        item?.let {
            val index = items.indexOf(item)
            if (index != -1) {
                removeItemAt(index)
                showEmptyState()
            }
        }
    }

    fun removeItemAt(pos: Int) {
        if (pos in 0 until itemCount) {
            items.removeAt(pos)
            notifyItemRemoved(pos)
            showEmptyState()
        }
    }

    fun removeItems(items: List<StableId>?) {
        items?.let { swapItems(this.items.filter { items.contains(it) }) }
    }

    /** fromPos index INCLUDED toPos index NOT_INCLUDED*/
    fun removeRange(fromPos: Int, toPos: Int) {
        if (fromPos <= toPos && fromPos in 0 until itemCount && toPos in 0 until itemCount) {
            items = items.filterIndexed { index, _ -> index in fromPos until toPos } as MutableList<StableId>
            notifyItemRangeRemoved(fromPos, toPos - fromPos)
        } else
            Timber.e("from index is less than to index")

        showEmptyState()
    }


    fun swapItems(items: List<StableId>?) {
        items?.let {
            val diffCallback = DiffCallback(this.items, items)
            val res = DiffUtil.calculateDiff(diffCallback)
            this.items.clear()
            this.items.addAll(items)
            res.dispatchUpdatesTo(this)
            showEmptyState()
        }
    }

    fun setEmptyListCell(viewType: StableId) {
        emptyListCell = viewType
    }


    fun clearAllItems() {
        val initSize = itemCount
        items.clear()
        notifyItemRangeRemoved(0, initSize)
    }


    private fun showEmptyState() {
        if (itemCount == 0) {
            items.add(emptyListCell)
            notifyItemInserted(0)
        }
    }


}