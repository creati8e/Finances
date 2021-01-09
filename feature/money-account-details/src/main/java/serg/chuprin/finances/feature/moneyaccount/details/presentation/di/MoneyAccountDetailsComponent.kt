package serg.chuprin.finances.feature.moneyaccount.details.presentation.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.feature.moneyaccount.details.presentation.model.viewmodel.MoneyAccountDetailsViewModel
import serg.chuprin.finances.feature.moneyaccount.details.presentation.view.MoneyAccountDetailsFragment
import serg.chuprin.finances.feature.moneyaccounts.details.dependencies.MoneyAccountDetailsDependencies
import serg.chuprin.finances.injector.Injector

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

        fun get(screenArguments: MoneyAccountDetailsScreenArguments): MoneyAccountDetailsComponent {
            return DaggerMoneyAccountDetailsComponent
                .factory()
                .newComponent(
                    moneyAccountId = screenArguments.moneyAccountId.value,
                    dependencies = Injector.getMoneyAccountDetailsDependencies()
                )
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: MoneyAccountDetailsDependencies,
            // Dagger failing to bind inline class. So bind string instead.
            @BindsInstance moneyAccountId: String
        ): MoneyAccountDetailsComponent

    }

}