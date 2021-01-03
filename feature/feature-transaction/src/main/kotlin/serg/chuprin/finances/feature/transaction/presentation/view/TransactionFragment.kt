package serg.chuprin.finances.feature.transaction.presentation.view

import android.os.Bundle
import android.view.View
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_transaction.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.di.TransactionComponent

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {

    private val viewModel by viewModelFromComponent { TransactionComponent.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
        }

        with(viewModel) {
            chosenDateLiveData(chosenDateTextView::setText)
            chosenCategoryLiveData(chosenCategoryTextView::setText)
        }
    }

}