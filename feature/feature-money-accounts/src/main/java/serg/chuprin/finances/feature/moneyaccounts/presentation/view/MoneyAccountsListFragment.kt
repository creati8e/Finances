package serg.chuprin.finances.feature.moneyaccounts.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.transition.addListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_money_accounts_list.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.SHARED_ELEMENT_TRANSITION_DURATION
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.feature.moneyaccounts.R
import serg.chuprin.finances.feature.moneyaccounts.di.MoneyAccountsListComponent
import serg.chuprin.finances.feature.moneyaccounts.presentation.view.adapter.renderer.MoneyAccountCellRenderer

/**
 * Created by Sergey Chuprin on 26.04.2020.
 */
class MoneyAccountsListFragment : BaseFragment(R.layout.fragment_money_accounts_list) {

    private val viewModel by viewModelFromComponent { MoneyAccountsListComponent.get() }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(MoneyAccountCellRenderer())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext()).apply {
            containerColor = Color.WHITE
            duration = SHARED_ELEMENT_TRANSITION_DURATION
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            startShapeAppearanceModel = ShapeAppearanceModel().withCornerSize(
                requireContext().dpToPx(16).toFloat()
            )
            addListener(onEnd = { accountCreationFab?.show() })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(moneyAccountsRecyclerView) {
            adapter = cellsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.cellsLiveData(cellsAdapter::setItems)
    }

}