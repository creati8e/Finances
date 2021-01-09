package serg.chuprin.finances.core.impl.presentation.model.formatter

import android.graphics.Color
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
internal class CategoryColorFormatterImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : CategoryColorFormatter {

    override fun format(category: Category?): Int {
        return runCatching { category?.colorHex?.let(Color::parseColor) }.getOrNull()
            ?: resourceManger.getColor(R.color.colorNoCategory)
    }

}