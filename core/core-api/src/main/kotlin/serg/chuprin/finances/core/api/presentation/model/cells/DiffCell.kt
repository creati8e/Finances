package serg.chuprin.finances.core.api.presentation.model.cells

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Sergey Chuprin on 31.01.2019.
 *
 * Represents cell which could be identified among other cells when using [DiffUtil].
 */
interface DiffCell<T> : BaseCell {

    val diffCellId: T

}