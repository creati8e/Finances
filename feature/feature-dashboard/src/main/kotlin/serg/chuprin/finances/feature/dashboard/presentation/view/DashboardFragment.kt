package serg.chuprin.finances.feature.dashboard.presentation.view

import android.os.Bundle
import android.view.View
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardComponent

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelFromComponent { DashboardComponent.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarImageView.clipToOutline = true
        viewModel.lastTransactionsLiveData { transactions ->
            Timber.d { "Got transactions in LiveData: $transactions" }
        }
    }

}