package serg.chuprin.finances.core.impl.data.mapper.category

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.impl.data.datasource.assets.CategoryAssetDto
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 09.01.2021.
 */
internal class CategoryAssetMapper @Inject constructor() {

    fun mapFromAsset(categoryAssetDto: CategoryAssetDto, ownerId: Id): Category? {
        val type = if (categoryAssetDto.isIncome) {
            CategoryType.INCOME
        } else {
            CategoryType.EXPENSE
        }
        return Category.create(
            id = categoryAssetDto.id,
            type = type,
            name = categoryAssetDto.name,
            colorHex = categoryAssetDto.colorHex,
            ownerId = ownerId.value,
            parentCategoryId = categoryAssetDto.parentCategoryId
        )
    }

}