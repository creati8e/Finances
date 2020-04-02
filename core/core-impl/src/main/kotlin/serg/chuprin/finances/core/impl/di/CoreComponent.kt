package serg.chuprin.finances.core.impl.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.domain.di.CoreDependencies
import serg.chuprin.finances.core.api.domain.di.scopes.AppScope
import serg.chuprin.finances.core.impl.di.module.CoreGatewaysModule

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@AppScope
@Component(modules = [CoreGatewaysModule::class])
interface CoreComponent : CoreDependencies {

    companion object {

        private lateinit var component: CoreComponent

        fun get(): CoreDependencies = component

        fun init(context: Context) {
            component = DaggerCoreComponent.builder().context(context).build()
        }

    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): CoreComponent

    }

}