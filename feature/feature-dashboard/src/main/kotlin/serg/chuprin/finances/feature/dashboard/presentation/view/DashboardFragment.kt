package serg.chuprin.finances.feature.dashboard.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.transform.RoundedCornersTransformation
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.navigation.DashboardNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.extensions.getDimenDpFloat
import serg.chuprin.finances.core.api.presentation.view.popup.menu.PopupMenuWindow
import serg.chuprin.finances.core.api.presentation.view.setExitSharedElementTransition
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardComponent
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardEvent
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.dashboard
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    @Inject
    lateinit var navigation: DashboardNavigation

    private val viewModel by viewModelFromComponent { component }

    private val component by component { DashboardComponent.get() }

    private lateinit var cellsAdapter: DiffMultiViewAdapter<BaseCell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setExitSharedElementTransition()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cellsAdapter = dashboard(viewModel)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        recyclerView.adapter = cellsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
            userPhotoLiveData { photoUrl ->
                userPhotoImageView.load(photoUrl) {
                    val radius = requireContext().getDimenDpFloat(R.dimen.cornerRadius)
                    transformations(RoundedCornersTransformation(radius))
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
                // TODO: Optimize.
                PopupMenuWindow(
                    event.menuCells.toTypedArray(),
                    { cell ->
                        viewModel.dispatchIntent(DashboardIntent.ClickOnPeriodTypeCell(cell))
                    }
                ).show(anchorView)
            }
            DashboardEvent.NavigateToMoneyAccountsListScreen -> {
                navigation.navigateToMoneyAccountsList(
                    navController,
                    recyclerView.moneyAccountsSubtitleLayout
                )
            }
            is DashboardEvent.NavigateToMoneyAccountDetailsScreen -> {
                val sharedElementView = recyclerView.findViewWithTag<View>(event.transitionName)
                navigation.navigateToMoneyAccountDetails(
                    navController,
                    event.moneyAccountId,
                    event.transitionName,
                    sharedElementView
                )
            }
            DashboardEvent.NavigateToMoneyAccountCreationScreen -> {
                val tag = resources.getString(R.string.transition_money_account_creation)
                val sharedElementView = recyclerView.findViewWithTag<View>(tag)
                navigation.navigateToMoneyAccountCreation(navController, sharedElementView)
            }
            is DashboardEvent.NavigateToTransactionsReportScreen -> {
                val transitionName = event.arguments.transitionName
                val sharedElementView = recyclerView.findViewWithTag<View>(transitionName)
                navigation.navigateToTransactionsReport(
                    navController,
                    event.arguments,
                    sharedElementView
                )
            }
        }
    }

}