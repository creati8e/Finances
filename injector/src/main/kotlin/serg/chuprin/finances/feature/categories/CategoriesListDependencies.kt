package serg.chuprin.finances.feature.categories

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
interface CategoriesListDependencies {

    val categoryColorFormatter: CategoryColorFormatter

    val userRepository: UserRepository
    val categoryRepository: TransactionCategoryRepository

}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface CategoriesListDependenciesComponent : CategoriesListDependencies