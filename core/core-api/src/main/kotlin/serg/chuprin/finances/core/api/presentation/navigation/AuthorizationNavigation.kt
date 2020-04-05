package serg.chuprin.finances.core.api.presentation.navigation

import androidx.navigation.NavController

/**
 * Created by Sergey Chuprin on 03.04.2020.
 *
 * Represents actions available to perform from authorization screen.
 */
interface AuthorizationNavigation {

    fun navigateToDashboard(navController: NavController)

    fun navigateToOnboarding(navController: NavController)

}