package serg.chuprin.finances.core.api.presentation.view.adapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import serg.chuprin.adapter.base.AbsMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ContainerRenderer

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
@Suppress("LeakingThis", "unused")
open class DiffMultiViewAdapter<T : Any> : AbsMultiViewAdapter<T> {

    constructor(config: AsyncDifferConfig<T>) {
        differ = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    constructor(itemCallback: DiffUtil.ItemCallback<T>) {
        differ = AsyncListDiffer(this, itemCallback)
    }

    val items: List<T>
        get() = differ.currentList

    private val differ: AsyncListDiffer<T>

    override fun getItem(position: Int): T = differ.currentList[position]

    override fun getItemCount(): Int = differ.currentList.size

    fun getItemOrNull(position: Int): T? = differ.currentList.getOrNull(position)

    fun setItems(items: List<T>) = differ.submitList(items)

    fun setItems(items: List<T>, onCommit: () -> Unit) {
        differ.submitList(items, onCommit)
    }

    inline fun <reified C : BaseCell> registerRenderer(layoutRes: Int) {
        rendererDelegate.registerRenderer(object : ContainerRenderer<C>() {
            override val type: Int = layoutRes
        })
    }

}