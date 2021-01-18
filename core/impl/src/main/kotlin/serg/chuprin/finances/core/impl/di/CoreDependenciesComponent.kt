package serg.chuprin.finances.core.impl.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.Initializer
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.impl.di.module.*

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@AppScope
@Component(
    modules = [
        CoreUtilsModule::class,
        CoreGatewaysModule::class,
        CoreServicesModule::class,
        CoreManagersModule::class,
        CoreBuildersModule::class,
        CoreDataSourceModule::class,
        CoreFormattersModule::class,
        CorePreferencesModule::class,
        CoreRepositoriesModule::class,
        CoreInitializersModule::class
    ]
)
interface CoreDependenciesComponent : CoreDependenciesProvider {

    companion object {

        private lateinit var component: CoreDependenciesComponent

        fun get(): CoreDependenciesComponent = component

        fun init(context: Context) {
            component = DaggerCoreDependenciesComponent
                .builder()
                .context(context)
                .build()
        }

    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): CoreDependenciesComponent

    }

    val appInitializer: Initializer

}