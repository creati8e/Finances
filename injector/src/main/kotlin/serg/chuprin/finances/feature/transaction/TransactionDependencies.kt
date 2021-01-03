package serg.chuprin.finances.feature.transaction

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
interface TransactionDependencies {

    val resourceManger: ResourceManger

}

@Component(dependencies = [CoreDependenciesProvider::class])
interface TransactionDependenciesComponent : TransactionDependencies