package serg.chuprin.finances.feature.transaction.di

import dagger.BindsInstance
import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.feature.transaction.TransactionDependencies
import serg.chuprin.finances.feature.transaction.presentation.model.viewmodel.TransactionViewModel
import serg.chuprin.finances.feature.transaction.presentation.view.TransactionFragment
import serg.chuprin.finances.injector.Injector

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
@ScreenScope
@Component(
    modules = [TransactionModule::class],
    dependencies = [TransactionDependencies::class]
)
interface TransactionComponent :
    InjectableComponent<TransactionFragment>,
    ViewModelComponent<TransactionViewModel> {

    companion object {

        fun get(screenArguments: TransactionScreenArguments): TransactionComponent {
            return DaggerTransactionComponent
                .factory()
                .newComponent(Injector.getTransactionDependencies(), screenArguments)
        }

    }

    @Component.Factory
    interface Factory {

        fun newComponent(
            dependencies: TransactionDependencies,
            @BindsInstance
            screenArguments: TransactionScreenArguments
        ): TransactionComponent

    }

}