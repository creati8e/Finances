package serg.chuprin.finances.feature.moneyaccounts.details.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface MoneyAccountDetailsDependencies {

    val moneyAccountService: MoneyAccountService

}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MoneyAccountDetailsDependenciesComponent : MoneyAccountDetailsDependencies