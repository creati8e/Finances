package serg.chuprin.finances.feature.categories.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryType
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithChildren
import serg.chuprin.finances.feature.categories.impl.domain.service.CategoriesDataService
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class GetAllUserCategoriesUseCase @Inject constructor(
    private val dataService: CategoriesDataService
) {

    fun execute(categoryType: TransactionCategoryType?): Flow<Set<TransactionCategoryWithChildren>> {
        return dataService.dataFlow(categoryType)
    }

}