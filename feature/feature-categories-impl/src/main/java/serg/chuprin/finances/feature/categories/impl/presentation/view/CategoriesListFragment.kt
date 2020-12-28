package serg.chuprin.finances.feature.categories.impl.presentation.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_categories_list.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.fragmentArguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar
import serg.chuprin.finances.feature.categories.impl.R
import serg.chuprin.finances.feature.categories.impl.presentation.di.CategoriesListComponent
import serg.chuprin.finances.feature.categories.impl.presentation.model.cell.ParentCategoryCell
import serg.chuprin.finances.feature.categories.impl.presentation.model.store.CategoriesListIntent
import serg.chuprin.finances.feature.categories.impl.presentation.view.adapter.renderer.ChildCategoryCellRenderer
import serg.chuprin.finances.feature.categories.impl.presentation.view.adapter.renderer.ParentCategoryCellRenderer

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesListFragment : BaseFragment(R.layout.fragment_categories_list) {

    private val viewModel by viewModelFromComponent {
        CategoriesListComponent.get(fragmentArguments())
    }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback()).apply {
        registerRenderer(ChildCategoryCellRenderer())
        registerRenderer(ParentCategoryCellRenderer())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
            recyclerView.fit { Edge.Bottom }
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.categories_list_toolbar_title)
        }

        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(viewModel) {
            cellsLiveData(cellsAdapter::setItems)
        }

        cellsAdapter.clickListener = { cell, cellView, _ ->
            if (cell is ParentCategoryCell && cellView.id == R.id.expansionArrowImageView) {
                viewModel.dispatchIntent(
                    CategoriesListIntent.ClickOnParentCategoryExpansionToggle(cell)
                )
            }
        }
    }

}