package serg.chuprin.finances.feature.transactions.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transactions_report.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.feature.transactions.presentation.TransactionReportNavigation
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.onScroll
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.di.TransactionsReportComponent
import serg.chuprin.finances.feature.transactions.presentation.model.TransactionReportHeader
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportEvent
import serg.chuprin.finances.feature.transactions.presentation.model.store.TransactionsReportIntent
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.TransactionReportCellsAdapter
import javax.inject.Inject


/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
class TransactionsReportFragment : BaseFragment(R.layout.fragment_transactions_report) {

    @Inject
    lateinit var navigation: TransactionReportNavigation

    private val screenArguments by arguments<TransactionsReportScreenArguments>()

    private val cellsAdapter = TransactionReportCellsAdapter(
        onTransactionClicked = { transactionCell ->
            viewModel.dispatchIntent(
                TransactionsReportIntent.ClickOnTransactionCell(transactionCell)
            )
        },
        onChartCellClicked = {
            viewModel.dispatchIntent(TransactionsReportIntent.ClickOnDataChartCell(it))
        }
    )

    private val viewModel by viewModelFromComponent { component }

    private val component by component {
        TransactionsReportComponent.get(screenArguments, findComponentDependencies())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
            recyclerView.fit { Edge.Bottom }
            fabLayout.fit { Edge.Bottom + Edge.Right }
        }

        view.transitionName = screenArguments.transitionName

        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        backButton.onClick {
            navController.navigateUp()
        }

        setupRecyclerView()

        with(viewModel) {
            headerLiveData(::showHeader)
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
        }
    }

    private fun handleEvent(event: TransactionsReportEvent) {
        return when (event) {
            is TransactionsReportEvent.NavigateToTransactionScreen -> {
                val sharedElementView = recyclerView.findViewWithTag<View>(
                    event.screenArguments.transitionName
                )
                navigation.navigateToTransaction(
                    navController,
                    event.screenArguments,
                    sharedElementView
                )
            }
        }
    }

    private fun showHeader(header: TransactionReportHeader) {
        titleTextView.text = header.title
        subtitleTextView.text = header.subtitle
        subtitleTextView.makeVisibleOrGone(header.subtitle.isNotEmpty())
    }

    private fun setupRecyclerView() {
        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                CellDividerDecoration(
                    requireContext(),
                    cellsAdapter,
                    marginEndDp = -8,
                    marginStartDp = 36
                )
            )
            onScroll { recyclerView, _, _ ->
                if (recyclerView.childCount > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findFirstVisibleItemPosition() == 0) {
                        this@TransactionsReportFragment.scrollToTopFab.hide()
                    } else {
                        this@TransactionsReportFragment.scrollToTopFab.show()
                    }
                } else {
                    this@TransactionsReportFragment.scrollToTopFab.hide()
                }
            }
        }
        scrollToTopFab.onClick {
            recyclerView.scrollToPosition(0)
            (requireView() as MotionLayout).transitionToStart()
        }
    }

}