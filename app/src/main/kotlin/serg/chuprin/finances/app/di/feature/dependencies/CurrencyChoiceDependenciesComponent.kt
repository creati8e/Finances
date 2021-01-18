package serg.chuprin.finances.app.di.feature.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.currency.choice.impl.di.CurrencyChoiceDependencies

/**
 * Created by Sergey Chuprin on 19.01.2021.
 */
@Component(dependencies = [CoreDependenciesProvider::class])
interface CurrencyChoiceDependenciesComponent : CurrencyChoiceDependencies