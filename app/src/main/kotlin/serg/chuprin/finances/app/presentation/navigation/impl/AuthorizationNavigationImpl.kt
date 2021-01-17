package serg.chuprin.finances.app.presentation.navigation.impl

import androidx.navigation.NavController
import serg.chuprin.finances.app.R
import serg.chuprin.finances.feature.authorization.presentation.AuthorizationNavigation

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
class AuthorizationNavigationImpl : AuthorizationNavigation {

    override fun navigateToAuthorizedGraph(navController: NavController) {
        navController.navigate(R.id.navigateToAuthorizedGraph)
    }

}