package serg.chuprin.finances.feature.moneyaccount.details.presentation.view

import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.moneyaccount.details.R
import serg.chuprin.finances.feature.moneyaccount.details.presentation.di.MoneyAccountDetailsComponent

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsFragment : BaseFragment(R.layout.fragment_money_account_details) {

    private val viewModel by viewModelFromComponent { MoneyAccountDetailsComponent.get() }

}