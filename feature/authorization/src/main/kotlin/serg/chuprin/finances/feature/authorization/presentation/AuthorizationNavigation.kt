package serg.chuprin.finances.feature.authorization.presentation

import androidx.navigation.NavController

/**
 * Created by Sergey Chuprin on 03.04.2020.
 *
 * Represents actions available to perform from authorization screen.
 */
interface AuthorizationNavigation {

    fun navigateToAuthorizedGraph(navController: NavController)

}