package serg.chuprin.finances.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import serg.chuprin.finances.app.R
import serg.chuprin.finances.app.di.MainComponent
import serg.chuprin.finances.app.model.AppLaunchState
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.R as CoreApiR

/**
 * Created by Sergey Chuprin on 01.04.2020.
 */
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModelFromComponent { MainComponent.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(CoreApiR.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.userAuthorizedLiveData.observe(this, Observer(::handleAppLaunchState))
    }

    private fun handleAppLaunchState(appLaunchState: AppLaunchState) {
        val navController = findNavController(R.id.rootFragmentContainer)
        return when (appLaunchState) {
            AppLaunchState.ONBOARDING -> {
                navController.setGraph(R.navigation.navigation_onboarding)
            }
            AppLaunchState.DASHBOARD -> {
                navController.setGraph(R.navigation.navigation_dashboard)
            }
            AppLaunchState.AUTHENTICATION -> {
                navController.setGraph(R.navigation.navigation_authorization)
            }
        }
    }

}