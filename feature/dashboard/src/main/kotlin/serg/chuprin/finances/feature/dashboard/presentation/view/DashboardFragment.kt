package serg.chuprin.finances.feature.dashboard.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.cell_widget_dashboard_money_accounts.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.AppDebugMenu
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.getDimenDpFloat
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onViewClick
import serg.chuprin.finances.core.api.presentation.view.popup.menu.PopupMenuWindow
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.dashboard.BuildConfig
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.di.DashboardComponent
import serg.chuprin.finances.feature.dashboard.presentation.DashboardNavigation
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardLoadingCell
import serg.chuprin.finances.feature.dashboard.presentation.model.cells.DashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardEvent
import serg.chuprin.finances.feature.dashboard.presentation.model.store.DashboardIntent
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.balance.DashboardBalanceWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.categories.DashboardCategoriesWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.diff.DashboardAdapterDiffCallback
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.moneyaccounts.DashboardMoneyAccountsWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.period.DashboardPeriodSelectorWidgetCellRenderer
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.transactions.DashboardRecentTransactionsWidgetCellRenderer
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    @Inject
    lateinit var appDebugMenu: AppDebugMenu

    @Inject
    lateinit var navigation: DashboardNavigation

    private val viewModel by viewModelFromComponent { component }

    private val component by component { DashboardComponent.get(findComponentDependencies()) }

    private lateinit var cellsAdapter: DiffMultiViewAdapter<BaseCell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
            recyclerView.fit { Edge.Bottom }
            transactionCreationFab.fit { Edge.Bottom + Edge.Left }
        }

        if (::cellsAdapter.isInitialized.not()) {
            cellsAdapter = createAdapter()
        }

        with(debugImageView) {
            onClick(appDebugMenu::open)
            makeVisibleOrGone(BuildConfig.IS_DEBUG_MENU_ENABLED)
        }

        userPhotoImageView.onViewClick {
            navigation.navigateToUserProfile(navController, it)
        }

        transactionCreationFab.onClick {
            viewModel.dispatchIntent(DashboardIntent.ClickOnTransactionCreationButton)
        }

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
            transactionCreationFabVisibilityLiveData { visible ->
                if (visible) {
                    transactionCreationFab.show()
                } else {
                    transactionCreationFab.hide()
                }
            }
            userPhotoLiveData { photoUrl ->
                userPhotoImageView.load(photoUrl) {
                    val radius = requireContext().getDimenDpFloat(R.dimen.radiusCornerDefault)
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
                PopupMenuWindow(
                    cells = event.menuCells,
                    callback = { cell ->
                        viewModel.dispatchIntent(DashboardIntent.ClickOnPeriodTypeCell(cell))
                    }
                ).show(anchorView)
            }
            is DashboardEvent.NavigateToMoneyAccountsListScreen -> {
                navigation.navigateToMoneyAccountsList(
                    navController,
                    event.screenArguments,
                    recyclerView.showAllAccountsButton
                )
            }
            is DashboardEvent.NavigateToMoneyAccountDetailsScreen -> {
                navigation.navigateToMoneyAccountDetails(
                    navController,
                    event.screenArguments,
                    recyclerView.findViewWithTag(event.screenArguments.transitionName)
                )
            }
            is DashboardEvent.NavigateToMoneyAccountCreationScreen -> {
                val sharedElementView = recyclerView.addAccountButton
                if (sharedElementView != null) {
                    navigation.navigateToMoneyAccount(
                        navController,
                        event.screenArguments,
                        sharedElementView
                    )
                } else {
                    navigation.navigateToMoneyAccount(navController, event.screenArguments)
                }
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
            is DashboardEvent.NavigateToTransactionScreen -> {
                val sharedElementView = when (event.screenArguments) {
                    is TransactionScreenArguments.Editing -> {
                        recyclerView.findViewWithTag<View>(
                            event.screenArguments.transitionName
                        )
                    }
                    is TransactionScreenArguments.Creation -> transactionCreationFab
                }

                navigation.navigateToTransaction(
                    navController,
                    event.screenArguments,
                    sharedElementView
                )
            }
        }
    }

    private fun createAdapter(): DiffMultiViewAdapter<BaseCell> {
        return DiffMultiViewAdapter(DashboardAdapterDiffCallback()).apply {
            registerRenderer<DashboardLoadingCell>(R.layout.cell_dashboard_loading)
            registerRenderer(
                ZeroDataCellRenderer(onButtonClick = {
                    viewModel.dispatchIntent(DashboardIntent.ClickOnZeroData)
                })
            )
            registerRenderer(
                DashboardBalanceWidgetCellRenderer(
                    clickOnCurrentPeriodIncomes = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodIncomesButton)
                    },
                    clickOnCurrentPeriodExpenses = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriodExpensesButton)
                    }
                )
            )
            registerRenderer(
                DashboardCategoriesWidgetCellRenderer(
                    onCategoryClicked = { categoryChipCell ->
                        viewModel.dispatchIntent(DashboardIntent.ClickOnCategory(categoryChipCell))
                    }
                )
            )
            registerRenderer(
                DashboardMoneyAccountsWidgetCellRenderer(
                    clickOnMoneyAccountCell = { cell ->
                        viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccount(cell))
                    },
                    clickOnWidgetSubtitle = { adapterPosition ->
                        val itemOrNull = getItemOrNull(adapterPosition)
                        val cell = itemOrNull as? DashboardWidgetCell.MoneyAccounts
                            ?: return@DashboardMoneyAccountsWidgetCellRenderer
                        viewModel.dispatchIntent(DashboardIntent.ToggleMoneyAccountsVisibility(cell))
                    },
                    clickOnCreateMoneyAccountButton = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnCreateMoneyAccountButton)
                    },
                    clickOnShowMoneyAccountsListButton = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnMoneyAccountsListButton)
                    }
                )
            )
            registerRenderer(
                DashboardPeriodSelectorWidgetCellRenderer(
                    clickOnCurrentPeriod = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnCurrentPeriod)
                    },
                    clickOnNextPeriod = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnNextPeriodButton)
                    },
                    clickOnPreviousPeriod = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnPreviousPeriodButton)
                    },
                    clickOnRestoreDefaultPeriod = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnRestoreDefaultPeriodButton)
                    }
                )
            )
            registerRenderer(
                DashboardRecentTransactionsWidgetCellRenderer(
                    onTransactionClick = { transactionCell ->
                        viewModel.dispatchIntent(
                            DashboardIntent.ClickOnRecentTransactionCell(transactionCell)
                        )
                    },
                    clickOnShowMoreTransactions = {
                        viewModel.dispatchIntent(DashboardIntent.ClickOnShowMoreTransactionsButton)
                    }
                )
            )
        }
    }

}