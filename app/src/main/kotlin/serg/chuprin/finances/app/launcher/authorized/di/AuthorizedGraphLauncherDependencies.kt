package serg.chuprin.finances.app.launcher.authorized.di

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.OnboardingRepository

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
interface AuthorizedGraphLauncherDependencies {
    val onboardingRepository: OnboardingRepository
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface AuthorizedGraphLauncherDependenciesComponent :
    AuthorizedGraphLauncherDependencies