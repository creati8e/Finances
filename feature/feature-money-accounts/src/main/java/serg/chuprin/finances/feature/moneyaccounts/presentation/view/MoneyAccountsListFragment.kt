package serg.chuprin.finances.feature.moneyaccounts.presentation.view

import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListComponent

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class MoneyAccountsListFragment : BaseFragment(R.layout.fragment_money_accounts_list) {

    private val viewModel by viewModelFromComponent { MoneyAccountsListComponent.get() }

}