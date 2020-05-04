package serg.chuprin.finances.app.launcher.app

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.main.dependencies.AppLauncherDependencies
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
@Component(dependencies = [AppLauncherDependencies::class])
interface AppLauncherComponent : ViewModelComponent<AppLauncherViewModel> {

    companion object {
        fun get(): AppLauncherComponent {
            return DaggerAppLauncherComponent.builder()
                .appLauncherDependencies(Injector.getAppLauncherDependencies())
                .build()
        }
    }

}