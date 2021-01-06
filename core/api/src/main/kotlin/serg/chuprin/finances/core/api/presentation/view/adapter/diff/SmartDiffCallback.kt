package serg.chuprin.finances.core.api.presentation.view.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DiffCell

/**
 * Created by Sergey Chuprin on 07.12.2020.
 *
 * Diff callback that disallows you to override [areItemsTheSame] and other method
 * for providing your custom diffing logic and forces you to use concise API:
 * [addItemsComparator], [addContentsComparator] and [addPayloadProvider].
 *
 * Example of usage: create subclass of [SmartDiffCallback] and add your comparators in 'init' block.
 *
 * init {
 *     addAreItemsTheSameComparator<YourCellType> { oldCell, newCell -> your logic }
 *     addContentsComparator<YourCellType>(YourCellType::equals)
 *     addPayloadProvider<YourCellType> { oldCell, newCell -> your payload }
 * }
 */
abstract class SmartDiffCallback<T : BaseCell> : DiffUtil.ItemCallback<T>() {

    protected val payloadProviders = mutableMapOf<Class<T>, (T, T) -> Any?>()
    protected val itemComparators = mutableMapOf<Class<T>, (T, T) -> Boolean>()
    protected val contentComparators = mutableMapOf<Class<T>, (T, T) -> Boolean>()

    final override fun areItemsTheSame(oldCell: T, newCell: T): Boolean {
        return when {
            oldCell.javaClass != newCell.javaClass -> false
            oldCell is DiffCell<*> && newCell is DiffCell<*> -> {
                oldCell.diffCellId == newCell.diffCellId
            }
            else -> itemComparators[oldCell::class.java]?.invoke(oldCell, newCell) ?: true
        }
    }

    final override fun areContentsTheSame(oldCell: T, newCell: T): Boolean {
        if (oldCell is DiffCell<*> && newCell is DiffCell<*>) {
            @Suppress("ReplaceCallWithBinaryOperator")
            return oldCell.equals(newCell)
        }
        return contentComparators[oldCell::class.java]?.invoke(oldCell, newCell) ?: false
    }

    final override fun getChangePayload(oldCell: T, newCell: T): Any? {
        if (oldCell.javaClass == newCell.javaClass) {
            return payloadProviders[oldCell::class.java]?.invoke(oldCell, newCell)
        }
        return super.getChangePayload(oldCell, newCell)
    }

    protected inline fun <reified R : T> addItemsComparator(
        noinline check: (oldCell: R, newCell: R) -> Boolean
    ) {
        @Suppress("UNCHECKED_CAST")
        itemComparators[R::class.java as Class<T>] = check as (T, T) -> Boolean
    }

    protected inline fun <reified R : T> addContentsComparator(
        noinline check: (oldCell: R, newCell: R) -> Boolean
    ) {
        @Suppress("UNCHECKED_CAST")
        contentComparators[R::class.java as Class<T>] = check as (T, T) -> Boolean
    }

    protected inline fun <reified R : T> addPayloadProvider(
        noinline check: (oldCell: R, newCell: R) -> Any?
    ) {
        @Suppress("UNCHECKED_CAST")
        payloadProviders[R::class.java as Class<T>] = check as (T, T) -> Any?
    }

}