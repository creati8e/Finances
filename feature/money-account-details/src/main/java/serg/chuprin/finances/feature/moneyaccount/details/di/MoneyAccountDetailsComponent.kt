package serg.chuprin.finances.feature.moneyaccount.details.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.viewmodel.MoneyAccountDetailsViewModel
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
@ScreenScope
@Component(
    modules = [MoneyAccountDetailsModule::class],
    dependencies = [MoneyAccountDetailsDependencies::class]
)
interface MoneyAccountDetailsComponent :
    InjectableComponent<MoneyAccountDetailsFragment>,
    ViewModelComponent<MoneyAccountDetailsViewModel> {

    companion object {

        fun get(
            screenArguments: MoneyAccountDetailsScreenArguments,
            dependencies: MoneyAccountDetailsDependencies
        ): MoneyAccountDetailsComponent {
            return DaggerMoneyAccountDetailsComponent
                .factory()
                .newComponent(
                    dependencies = dependencies,
                    screenArguments = screenArguments
                )
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: MoneyAccountDetailsDependencies,
            @BindsInstance screenArguments: MoneyAccountDetailsScreenArguments
        ): MoneyAccountDetailsComponent

    }

}