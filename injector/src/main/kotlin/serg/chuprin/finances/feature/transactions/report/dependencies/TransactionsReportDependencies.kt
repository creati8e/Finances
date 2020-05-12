package serg.chuprin.finances.feature.transactions.report.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
interface TransactionsReportDependencies

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface TransactionsReportDependenciesComponent : TransactionsReportDependencies