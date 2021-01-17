package serg.chuprin.finances.app.di.navigation

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.AppScope

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@AppScope
@Component(modules = [AppNavigationModule::class])
interface AppNavigationComponent : AppNavigationProvider {

    companion object {
        val instance: AppNavigationComponent = DaggerAppNavigationComponent.create()
    }

}