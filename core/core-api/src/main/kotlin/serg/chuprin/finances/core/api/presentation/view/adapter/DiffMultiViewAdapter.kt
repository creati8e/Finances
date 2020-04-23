package serg.chuprin.finances.core.api.presentation.view.adapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import serg.chuprin.adapter.base.AbsMultiViewAdapter

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
@Suppress("LeakingThis", "unused")
open class DiffMultiViewAdapter<T : Any> : AbsMultiViewAdapter<T> {

    constructor(config: AsyncDifferConfig<T>) {
        differ = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    constructor(itemCallback: DiffUtil.ItemCallback<T>) {
        differ = AsyncListDiffer<T>(this, itemCallback)
    }

    val items: List<T> get() = differ.currentList

    private val differ: AsyncListDiffer<T>

    override fun getItem(position: Int): T = differ.currentList[position]

    fun getItemOrNull(position: Int): T? = differ.currentList.getOrNull(position)

    override fun getItemCount(): Int = differ.currentList.size

    fun setItems(items: List<T>) = differ.submitList(items)
}