package serg.chuprin.finances.feature.wallet.creation.presentation

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_money_account_creation.*
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.wallet.creation.R

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
class MoneyAccountCreationFragment : BaseFragment(R.layout.fragment_money_account_creation) {

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

    override fun onDestroy() {
        super.onDestroy()
        setupToolbar(null)
    }

}