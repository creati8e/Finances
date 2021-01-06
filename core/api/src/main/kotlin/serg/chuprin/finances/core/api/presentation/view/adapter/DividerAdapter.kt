package serg.chuprin.finances.core.api.presentation.view.adapter

/**
 * Created by Sergey Chuprin on 09.08.2019.
 */
interface DividerAdapter {

    /**
     * @return true if bottom divider for the cell at position is required.
     */
    fun shouldDrawDividerForCellAt(adapterPosition: Int): Boolean

}