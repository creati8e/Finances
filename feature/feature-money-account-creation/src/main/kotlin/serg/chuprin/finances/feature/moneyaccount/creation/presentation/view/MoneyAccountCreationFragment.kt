package serg.chuprin.finances.feature.moneyaccount.creation.presentation.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_money_account_creation.*
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.moneyaccount.creation.R
import serg.chuprin.finances.feature.moneyaccount.creation.presentation.di.MoneyAccountCreationComponent

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
class MoneyAccountCreationFragment : BaseFragment(R.layout.fragment_money_account_creation) {

    private val viewModel by viewModelFromComponent { MoneyAccountCreationComponent.get() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
    }

}