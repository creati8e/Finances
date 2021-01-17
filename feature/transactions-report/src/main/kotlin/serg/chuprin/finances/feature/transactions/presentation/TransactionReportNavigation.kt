package serg.chuprin.finances.feature.transactions.presentation

import android.view.View
import androidx.navigation.NavController
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
interface TransactionReportNavigation {

    fun navigateToTransaction(
        navController: NavController,
        arguments: TransactionScreenArguments,
        sharedElementView: View
    )

}