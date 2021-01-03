package serg.chuprin.finances.core.api.presentation.navigation

import androidx.navigation.NavController

/**
 * Created by Sergey Chuprin on 03.04.2020.
 *
 * Represents actions available to perform from authorization screen.
 */
// TODO: Move all navigation interfaces to feature modules.
interface AuthorizationNavigation {

    fun navigateToAuthorizedGraph(navController: NavController)

}