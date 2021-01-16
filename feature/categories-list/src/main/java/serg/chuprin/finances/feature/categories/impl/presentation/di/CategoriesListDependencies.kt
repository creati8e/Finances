package serg.chuprin.finances.feature.categories.impl.presentation.di

import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
interface CategoriesListDependencies {
    val categoryColorFormatter: CategoryColorFormatter

    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
}

interface CategoriesListDependenciesProvider {
    val categoriesListDependencies: CategoriesListDependencies
}