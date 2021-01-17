package serg.chuprin.finances.feature.categories.list.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.category.Category
import serg.chuprin.finances.core.api.domain.model.category.CategoryType
import serg.chuprin.finances.core.api.domain.model.category.query.CategoriesQuery
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
class SearchUserCategoriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) {

    private companion object {

        private val categoryNameComparator = compareBy<Category> {
            it.name
        }

    }

    fun execute(
        nameQuery: String,
        categoryType: CategoryType?
    ): Flow<Collection<Category>> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest { user ->
                categoryRepository
                    .categoriesFlow(
                        CategoriesQuery(
                            ownerId = user.id,
                            type = categoryType,
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