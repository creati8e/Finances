package serg.chuprin.finances.feature.transactions.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transactions_report.*
import kotlinx.android.synthetic.main.fragment_transactions_report.view.*
import serg.chuprin.finances.core.api.presentation.extensions.arguments
import serg.chuprin.finances.core.api.presentation.extensions.setToolbarTitle
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionsReportScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.onScroll
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.transactions.R
import serg.chuprin.finances.feature.transactions.di.TransactionsReportComponent
import serg.chuprin.finances.feature.transactions.presentation.model.cells.TransactionReportChartCell
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.TransactionReportCellsAdapter
import serg.chuprin.finances.feature.transactions.presentation.view.adapter.renderer.TransactionChartCellRenderer

/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
class TransactionsReportFragment : BaseFragment(R.layout.fragment_transactions_report) {

    private val screenArguments by arguments<TransactionsReportScreenArguments>()

    private val transactionCellsAdapter = TransactionReportCellsAdapter()

    private val chartCellsAdapter = DiffMultiViewAdapter(
        DiffCallback<TransactionReportChartCell>()
    ).apply {
        registerRenderer(TransactionChartCellRenderer())
    }

    private val viewModel by viewModelFromComponent {
        TransactionsReportComponent.get(screenArguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)!!.apply {
            constraintLayout.transitionName = screenArguments.transitionName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSharedElementTransitions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
            filterFab.fit { Edge.Bottom + Edge.Right }
            transactionsRecyclerView.fit { Edge.Bottom }
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        setupChart()
        setupTransactionsList()

        with(viewModel) {
            chartCellsLiveData(chartCellsAdapter::setItems)
            transactionsListCellsLiveData(transactionCellsAdapter::setItems)
            headerLiveData { header ->
                setToolbarTitle(header.title)
            }
        }
    }

    private fun setupChart() {
        with(chartRecyclerView) {
            adapter = chartCellsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupTransactionsList() {
        with(transactionsRecyclerView) {
            adapter = transactionCellsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                CellDividerDecoration(
                    requireContext(),
                    transactionCellsAdapter,
                    marginEndDp = -8,
                    marginStartDp = 36
                )
            )
            onScroll { _, _, dy ->
                if (dy > 0) {
                    this@TransactionsReportFragment.filterFab.hide()
                } else if (dy < 0) {
                    this@TransactionsReportFragment.filterFab.show()
                }
            }
        }
    }

}