package serg.chuprin.finances.app.di

import dagger.Component
import serg.chuprin.finances.app.model.viewmodel.MainViewModel
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.main.dependencies.MainDependencies
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
@Component(dependencies = [MainDependencies::class])
interface MainComponent : ViewModelComponent<MainViewModel> {

    companion object {

        fun get(): MainComponent {
            return DaggerMainComponent
                .builder()
                .mainDependencies(Injector.getMainDependencies())
                .build()
        }

    }

}