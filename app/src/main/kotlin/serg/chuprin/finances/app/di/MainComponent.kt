package serg.chuprin.finances.app.di

import dagger.Component
import serg.chuprin.finances.app.model.viewmodel.MainViewModel
import serg.chuprin.finances.core.api.domain.di.CoreGatewaysProvider
import serg.chuprin.finances.core.api.domain.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.impl.di.CoreComponent

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
@Component(dependencies = [MainComponent.Dependencies::class])
interface MainComponent : ViewModelComponent<MainViewModel> {

    interface Dependencies {
        val authenticationGateway: AuthenticationGateway
    }

    @Component(dependencies = [CoreGatewaysProvider::class])
    interface DependenciesComponent : Dependencies

    companion object {

        fun get(): MainComponent {
            return DaggerMainComponent
                .builder()
                .dependencies(
                    DaggerMainComponent_DependenciesComponent
                        .builder()
                        .coreGatewaysProvider(CoreComponent.get())
                        .build()
                )
                .build()
        }

    }

}