package serg.chuprin.finances.feature.dashboard.presentation.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.popup.menu.PopupMenuWindow
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardComponent
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardEvent
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dsl.dashboard
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelFromComponent { DashboardComponent.get() }

    private val cellsAdapter by lazy { recyclerView.dashboard(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = cellsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        cellsAdapter.clickListener = { cell, clickedView, _ ->
            when (cell) {
                is DashboardWidgetCell.MoneyAccounts -> {
                    if (clickedView.id == R.id.subtitleLayout) {
                        viewModel.dispatchIntent(DashboardIntent.ToggleMoneyAccountsVisibility(cell))
                    }
                }
                is DashboardWidgetCell.Header -> {
                    when (clickedView.id) {
                        R.id.currentPeriodLayout -> {
                            viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriod)
                        }
                        R.id.nextPeriodButton -> {
                            viewModel.dispatchIntent(DashboardIntent.ClickOnNextPeriodButton)
                        }
                        R.id.previousPeriodButton -> {
                            viewModel.dispatchIntent(DashboardIntent.ClickOnPreviousPeriodButton)
                        }
                        R.id.restoreDefaultPeriodButton -> {
                            viewModel.dispatchIntent(DashboardIntent.ClickOnRestoreDefaultPeriodButton)
                        }
                    }
                }
            }
        }

        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
            userPhotoLiveData { photoUrl ->
                userPhotoImageView.load(photoUrl) {
                    error(CoreR.drawable.ic_user_photo_placeholder)
                    placeholder(CoreR.drawable.ic_user_photo_placeholder)
                }
            }
        }
    }

    private fun handleEvent(event: DashboardEvent) {
        return when (event) {
            is DashboardEvent.ShowPeriodTypesPopupMenu -> {
                val anchorView = recyclerView.findViewById<ViewGroup>(R.id.currentPeriodLayout)
                // TOTO: Optimize.
                PopupMenuWindow(
                    event.menuCells.toTypedArray(),
                    { cell -> viewModel.dispatchIntent(DashboardIntent.ClickOnPeriodTypeCell(cell)) }
                ).show(anchorView)
            }
        }
    }

}