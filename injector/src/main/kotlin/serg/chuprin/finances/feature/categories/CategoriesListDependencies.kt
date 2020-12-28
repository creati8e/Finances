package serg.chuprin.finances.feature.categories

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
interface CategoriesListDependencies

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface CategoriesListDependenciesComponent : CategoriesListDependencies