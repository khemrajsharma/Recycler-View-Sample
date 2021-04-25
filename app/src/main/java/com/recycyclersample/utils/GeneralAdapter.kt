package com.recycyclersample.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.recycyclersample.BR
import com.recycyclersample.callbacks.OnItemClickListener
import com.recycyclersample.models.ResultsItem
import com.recycyclersample.utils.GeneralAdapter.GRViewHolder

/**
 * Created by sud on 12/23/2017.
 */
/* general recyclerview adapter */
class GeneralAdapter : RecyclerView.Adapter<GRViewHolder> {
    private val itemList: ArrayList<ResultsItem?>?
    var onItemClickListener: OnItemClickListener<*>? = null

    @LayoutRes
    var itemLayout: Int

    constructor(
        @LayoutRes itemLayout: Int,
        itemList: ArrayList<ResultsItem?>?,
        onItemClickListener: OnItemClickListener<*>?
    ) {
        this.itemList = itemList
        this.onItemClickListener = onItemClickListener
        this.itemLayout = itemLayout
    }

    constructor(@LayoutRes itemLayout: Int, itemList: ArrayList<ResultsItem?>?) {
        this.itemList = itemList
        this.itemLayout = itemLayout
    }

    fun add(newList: ArrayList<ResultsItem?>?) {
        if (newList != null && newList.isNotEmpty()) {
            val oldSize = itemList!!.size
            itemList.addAll(newList)
            notifyItemRangeChanged(oldSize - 1, newList.size)
        }
    }

    fun add(item: ResultsItem) {
        itemList!!.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun add(item: ResultsItem, position: Int) {
        if (position > 0 && position <= itemList!!.size) {
            itemList.add(item)
            notifyItemInserted(position)
        }
    }

    fun remove(position: Int) {
        if (position > 0 && position < itemList!!.size) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun remove(item: ResultsItem) {
        val position = itemList!!.indexOf(item)
        if (position >= 0 && position < itemList.size) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun removeAll() {
        if (itemList == null) return
        val oldSize = itemList.size
        itemList.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GRViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return GRViewHolder(v)
    }

    override fun onBindViewHolder(holder: GRViewHolder, position: Int) {
        holder.binding!!.setVariable(BR.item, itemList!![position])
        if (onItemClickListener != null) {
            holder.binding!!.setVariable(BR.onItemClickListener, onItemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    class GRViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var binding: ViewDataBinding? = DataBindingUtil.bind(itemView!!)

    }
}