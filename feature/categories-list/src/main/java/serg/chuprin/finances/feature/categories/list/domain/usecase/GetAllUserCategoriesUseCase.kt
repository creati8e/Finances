package serg.chuprin.finances.feature.categories.list.domain.usecase

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.CategoryWithChildren
import serg.chuprin.finances.feature.categories.list.domain.service.CategoriesDataService
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class GetAllUserCategoriesUseCase @Inject constructor(
    private val dataService: CategoriesDataService
) {

    fun execute(categoryType: CategoryType?): Flow<Set<CategoryWithChildren>> {
        return dataService.dataFlow(categoryType)
    }

}