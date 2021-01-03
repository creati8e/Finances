package serg.chuprin.finances.feature.transaction.presentation.view

import android.os.Bundle
import android.view.View
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.transaction.R

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edgeToEdge {
            view.fit { Edge.Top }
        }
    }

}