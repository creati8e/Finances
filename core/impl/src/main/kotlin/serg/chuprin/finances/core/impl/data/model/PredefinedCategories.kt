package serg.chuprin.finances.core.impl.data.model

import serg.chuprin.finances.core.impl.data.datasource.assets.CategoryAssetDto

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal class PredefinedCategories(
    val incomeCategories: List<CategoryAssetDto>,
    val expenseCategories: List<CategoryAssetDto>
)