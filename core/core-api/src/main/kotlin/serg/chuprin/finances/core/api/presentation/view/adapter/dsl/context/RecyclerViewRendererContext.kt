package serg.chuprin.finances.core.api.presentation.view.adapter.dsl.context

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import serg.chuprin.adapter.ContainerHolder
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.dsl.marker.RecyclerViewRendererContextDslMarker
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick

/**
 * Created by Sergey Chuprin on 29.04.2020.
 *
 * Represents context for defining behavior for particular cell.
 */
@Suppress("unused")
@RecyclerViewRendererContextDslMarker
class RecyclerViewRendererContext<T : BaseCell>(
    /**
     * Adapter is required for getting cells when some view is clicked.
     */
    val adapter: DiffMultiViewAdapter<BaseCell>,
    /**
     * Type of cell which is being set up in this context.
     */
    private val cellClass: Class<T>
) {

    internal var onBind: ((ContainerHolder, cell: T, payloads: MutableList<Any>) -> Unit)? = null

    internal var onViewHolderCreated: (viewHolder: ContainerHolder) -> Unit = { viewHolder ->
        onCellViewHolderCreated?.invoke(viewHolder)
        onNestedAdapterCreated?.invoke(viewHolder)
    }

    private var nestedAdapter: DiffMultiViewAdapter<BaseCell>? = null
    private var onCellViewHolderCreated: ((viewHolder: ContainerHolder) -> Unit)? = null
    private var onNestedAdapterCreated: ((viewHolder: ContainerHolder) -> Unit)? = null

    /**
     * Sets cells to nested list.
     * Should be used only if nested list is set up.
     * @see [nestedHorizontalList], [nestedList], [nestedVerticalList] and similar.
     */
    fun setNestedCells(cells: List<BaseCell>) {
        requireNotNull(nestedAdapter) {
            "Unable to set cells to nested adapter because it is not set"
        }.setItems(cells)
    }

    /**
     * Defines behavior for binding data to view holder for particular cell.
     * This method is analogue to [RecyclerView.Adapter.onBindViewHolder].
     */
    fun bind(block: ContainerHolder.(cell: T, payloads: List<Any>) -> Unit) {
        require(onBind == null) {
            "Bind block with payload is already defined"
        }
        onBind = block
    }

    /**
     * Defines behavior for setting up views when view holder is created.
     * This method is analogue to [RecyclerView.Adapter.onCreateViewHolder].
     */
    fun setupViews(block: ContainerHolder.() -> Unit) {
        require(onCellViewHolderCreated == null) {
            "Setup views block is already defined"
        }
        onCellViewHolderCreated = block
    }

    /**
     * Sets click listener to particular [View] and passes cell associated with current adapter position.
     */
    fun <V : View> ContainerHolder.setClickListener(view: V, listener: (cell: T) -> Unit) {
        view.onClick {
            @Suppress("DEPRECATION")
            val cell = adapter.getItemOrNull(adapterPosition)
            val castedCell = cellClass.runCatching { cast(cell) }.getOrNull() ?: return@onClick
            listener(castedCell)
        }
    }

    // region Nested lists.
    // Methods for adding nested lists.
    // Basically methods requires recycler view and diff callback or adapter.

    fun nestedHorizontalList(
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        diffCallback: DiffCallback<BaseCell> = DiffCallback(),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addLinearNestedList(
            block = block,
            orientation = RecyclerView.HORIZONTAL,
            recyclerViewProvider = recyclerViewProvider,
            adapter = DiffMultiViewAdapter(diffCallback)
        )
    }

    fun nestedHorizontalList(
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        adapter: DiffMultiViewAdapter<BaseCell> = DiffMultiViewAdapter(DiffCallback()),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addLinearNestedList(
            block = block,
            adapter = adapter,
            orientation = RecyclerView.HORIZONTAL,
            recyclerViewProvider = recyclerViewProvider
        )
    }

    fun nestedVerticalList(
        adapter: DiffMultiViewAdapter<BaseCell> = DiffMultiViewAdapter(DiffCallback()),
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addLinearNestedList(
            block = block,
            adapter = adapter,
            orientation = RecyclerView.VERTICAL,
            recyclerViewProvider = recyclerViewProvider
        )
    }

    fun nestedVerticalList(
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        diffCallback: DiffCallback<BaseCell> = DiffCallback(),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addLinearNestedList(
            block = block,
            orientation = RecyclerView.VERTICAL,
            recyclerViewProvider = recyclerViewProvider,
            adapter = DiffMultiViewAdapter(diffCallback)
        )
    }

    fun nestedList(
        layoutManagerProvider: (Context) -> RecyclerView.LayoutManager,
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        diffCallback: DiffCallback<BaseCell> = DiffCallback(),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addNestedList(
            block = block,
            adapter = DiffMultiViewAdapter(diffCallback),
            recyclerViewProvider = recyclerViewProvider,
            layoutManagerProvider = layoutManagerProvider
        )
    }

    fun nestedList(
        layoutManagerProvider: (Context) -> RecyclerView.LayoutManager,
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        adapter: DiffMultiViewAdapter<BaseCell> = DiffMultiViewAdapter(DiffCallback()),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        addNestedList(adapter, block, recyclerViewProvider, layoutManagerProvider)
    }

    private fun addLinearNestedList(
        @RecyclerView.Orientation orientation: Int,
        recyclerViewProvider: (ContainerHolder) -> RecyclerView,
        adapter: DiffMultiViewAdapter<BaseCell> = DiffMultiViewAdapter(DiffCallback()),
        block: RecyclerViewNestedAdapterContext.() -> Unit
    ) {
        nestedList(
            block = block,
            adapter = adapter,
            recyclerViewProvider = recyclerViewProvider,
            layoutManagerProvider = { context ->
                LinearLayoutManager(context, orientation, false)
            }
        )
    }

    private fun RecyclerViewRendererContext<T>.addNestedList(
        adapter: DiffMultiViewAdapter<BaseCell>,
        block: RecyclerViewNestedAdapterContext.() -> Unit,
        recyclerViewProvider: ContainerHolder.() -> RecyclerView,
        layoutManagerProvider: (Context) -> RecyclerView.LayoutManager
    ) {
        require(nestedAdapter == null) {
            "Only single nested list is supported"
        }
        onNestedAdapterCreated = { viewHolder ->
            val adapterJustCreated = nestedAdapter == null
            if (nestedAdapter == null) {
                nestedAdapter = adapter
            }
            with(recyclerViewProvider(viewHolder)) {
                this.adapter = nestedAdapter
                layoutManager = layoutManagerProvider(context)
            }
            if (adapterJustCreated) {
                RecyclerViewNestedAdapterContext(nestedAdapter!!, viewHolder).apply(block)
            }
        }
    }

    // endregion

}