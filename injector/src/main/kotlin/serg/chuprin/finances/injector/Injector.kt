package serg.chuprin.finances.injector

import serg.chuprin.finances.core.impl.di.CoreDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.AppLauncherDependencies
import serg.chuprin.finances.feature.main.dependencies.AuthorizedGraphLauncherDependencies
import serg.chuprin.finances.feature.main.dependencies.DaggerAppLauncherDependenciesComponent
import serg.chuprin.finances.feature.main.dependencies.DaggerAuthorizedGraphLauncherDependenciesComponent
import serg.chuprin.finances.feature.moneyaccounts.creation.DaggerMoneyAccountCreationDependenciesComponent
import serg.chuprin.finances.feature.moneyaccounts.creation.MoneyAccountCreationDependencies
import serg.chuprin.finances.feature.moneyaccounts.details.dependencies.DaggerMoneyAccountDetailsDependenciesComponent
import serg.chuprin.finances.feature.moneyaccounts.details.dependencies.MoneyAccountDetailsDependencies
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.DaggerMoneyAccountsListDependenciesComponent
import serg.chuprin.finances.feature.moneyaccounts.list.dependencies.MoneyAccountsListDependencies
import serg.chuprin.finances.feature.onboarding.dependencies.DaggerOnboardingFeatureDependenciesComponent
import serg.chuprin.finances.feature.onboarding.dependencies.OnboardingFeatureDependencies
import serg.chuprin.finances.feature.transaction.DaggerTransactionDependenciesComponent
import serg.chuprin.finances.feature.transaction.TransactionDependencies
import serg.chuprin.finances.feature.transactions.report.dependencies.DaggerTransactionsReportDependenciesComponent
import serg.chuprin.finances.feature.transactions.report.dependencies.TransactionsReportDependencies
import serg.chuprin.finances.feature.userprofile.dependencies.DaggerUserProfileDependenciesComponent
import serg.chuprin.finances.feature.userprofile.dependencies.UserProfileDependencies

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
object Injector {

    fun getTransactionDependencies(): TransactionDependencies {
        return DaggerTransactionDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getUserProfileDependencies(): UserProfileDependencies {
        return DaggerUserProfileDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getAppLauncherDependencies(): AppLauncherDependencies {
        return DaggerAppLauncherDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getAuthorizedGraphLauncherDependencies(): AuthorizedGraphLauncherDependencies {
        return DaggerAuthorizedGraphLauncherDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getOnboardingFeatureDependencies(): OnboardingFeatureDependencies {
        return DaggerOnboardingFeatureDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getMoneyAccountsListDependencies(): MoneyAccountsListDependencies {
        return DaggerMoneyAccountsListDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getMoneyAccountDetailsDependencies(): MoneyAccountDetailsDependencies {
        return DaggerMoneyAccountDetailsDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getTransactionsReportDependencies(): TransactionsReportDependencies {
        return DaggerTransactionsReportDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

    fun getMoneyAccountCreationDependencies(): MoneyAccountCreationDependencies {
        return DaggerMoneyAccountCreationDependenciesComponent
            .builder()
            .coreDependenciesProvider(CoreDependenciesComponent.get())
            .build()
    }

}