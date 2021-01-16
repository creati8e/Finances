package serg.chuprin.finances.app.launcher.app

import android.os.Bundle
import serg.chuprin.finances.app.launcher.app.AppLauncherFragmentDirections.navigateFromAppLauncherToAuthorizedGraph
import serg.chuprin.finances.app.launcher.app.AppLauncherFragmentDirections.navigateFromAppLauncherToNotAuthorizedGraph
import serg.chuprin.finances.app.launcher.app.di.AppLauncherComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment

/**
 * Created by Sergey Chuprin on 04.05.2020.
 */
class AppLauncherFragment : BaseFragment() {

    private val viewModel by viewModelFromComponent { AppLauncherComponent.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isUserAuthorizedLiveData.observe(
            this,
            { isUserAuthorized ->
                val action = if (isUserAuthorized) {
                    navigateFromAppLauncherToAuthorizedGraph()
                } else {
                    navigateFromAppLauncherToNotAuthorizedGraph()
                }
                navController.navigate(action)
            }
        )
    }

}