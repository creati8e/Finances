package serg.chuprin.finances.feature.categories.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.query.TransactionCategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class SearchUserCategoriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: TransactionCategoryRepository
) {

    private companion object {

        private val categoryNameComparator = compareBy<TransactionCategory> {
            it.name
        }

    }

    fun execute(nameQuery: String): Flow<Collection<TransactionCategory>> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest { user ->
                categoryRepository
                    .categoriesFlow(
                        TransactionCategoriesQuery(
                            ownerId = user.id,
                            categoryIds = emptySet(),
                            searchByName = nameQuery
                        )
                    )
            }
            .map { queryResult ->
                queryResult.mapTo(
                    sortedSetOf(categoryNameComparator),
                    { (_, categoryWithParent) -> categoryWithParent.category }
                )
            }
    }

}