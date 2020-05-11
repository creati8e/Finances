package serg.chuprin.finances.feature.transactions.presentation.view

import android.os.Bundle
import com.github.ajalt.timberkt.Timber
import serg.chuprin.finances.core.api.presentation.extensions.arguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.core.api.presentation.view.setExitSharedElementTransition
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.presentation.arguments.TransactionsReportScreenArguments

/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
class TransactionsReportFragment : BaseFragment(R.layout.fragment_transactions_report) {

    private val screenArguments by arguments<TransactionsReportScreenArguments>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setExitSharedElementTransition()
        setEnterSharedElementTransition()
        Timber.d { "TransactionsReportFragment arguments: $screenArguments" }
    }

}