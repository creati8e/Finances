package serg.chuprin.finances.core.api.presentation.formatter

import androidx.annotation.ColorInt
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
interface CategoryColorFormatter {

    @ColorInt
    fun format(category: TransactionCategory?): Int

}