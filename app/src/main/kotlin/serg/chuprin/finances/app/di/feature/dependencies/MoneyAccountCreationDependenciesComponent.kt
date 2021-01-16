package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.di.MoneyAccountCreationDependencies

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */
@Component(dependencies = [CoreDependenciesProvider::class])
interface MoneyAccountCreationDependenciesComponent : MoneyAccountCreationDependencies