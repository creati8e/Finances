package serg.chuprin.finances.core.api.presentation.formatter

import androidx.annotation.ColorInt
import serg.chuprin.finances.core.api.domain.model.category.Category

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
interface CategoryColorFormatter {

    @ColorInt
    fun format(category: Category?): Int

}