package serg.chuprin.finances.app.di.navigation

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreNavigationProvider
import serg.chuprin.finances.core.api.di.scopes.AppScope

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
@AppScope
@Component(modules = [NavigationModule::class])
interface NavigationComponent : CoreNavigationProvider {

    companion object {
        val instance: NavigationComponent = DaggerNavigationComponent.create()
    }

}