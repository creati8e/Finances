package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.presentation.model.manager.ResourceManagerImpl

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
abstract class CoreManagersModule {

    @[Binds AppScope]
    abstract fun bindResourceManager(impl: ResourceManagerImpl): ResourceManger

}