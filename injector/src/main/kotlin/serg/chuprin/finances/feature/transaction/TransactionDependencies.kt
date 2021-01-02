package serg.chuprin.finances.feature.transaction

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
interface TransactionDependencies

@Component(dependencies = [CoreDependenciesProvider::class])
interface TransactionDependenciesComponent : TransactionDependencies