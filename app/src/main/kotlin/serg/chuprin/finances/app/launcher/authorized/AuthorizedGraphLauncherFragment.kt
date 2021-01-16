package serg.chuprin.finances.app.launcher.authorized

import android.os.Bundle
import serg.chuprin.finances.app.launcher.authorized.AuthorizedGraphLauncherFragmentDirections.navigateFromAuthorizedGraphToDashboard
import serg.chuprin.finances.app.launcher.authorized.AuthorizedGraphLauncherFragmentDirections.navigateFromAuthorizedGraphToOnboarding
import serg.chuprin.finances.app.launcher.authorized.di.AuthorizedGraphComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
class AuthorizedGraphLauncherFragment : BaseFragment() {

    private val viewModel by viewModelFromComponent { AuthorizedGraphComponent.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isOnboardingCompletedLiveData.observe(
            this,
            { isOnboardingCompleted ->
                val action = if (isOnboardingCompleted) {
                    navigateFromAuthorizedGraphToDashboard()
                } else {
                    navigateFromAuthorizedGraphToOnboarding()
                }
                navController.navigate(action)
            }
        )
    }

}