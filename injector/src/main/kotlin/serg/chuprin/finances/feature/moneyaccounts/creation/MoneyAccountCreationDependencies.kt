package serg.chuprin.finances.feature.moneyaccounts.creation

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
interface MoneyAccountCreationDependencies

@Component(dependencies = [CoreDependenciesProvider::class])
interface MoneyAccountCreationDependenciesComponent : MoneyAccountCreationDependencies