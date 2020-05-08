package serg.chuprin.finances.core.api.presentation.navigation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
interface DashboardNavigation {

    fun navigateToMoneyAccountsList(navController: NavController, vararg sharedElementView: View)

    fun navigateToMoneyAccountDetails(
        navController: NavController,
        moneyAccountId: Id,
        transitionName: String,
        vararg sharedElementView: View
    )

}