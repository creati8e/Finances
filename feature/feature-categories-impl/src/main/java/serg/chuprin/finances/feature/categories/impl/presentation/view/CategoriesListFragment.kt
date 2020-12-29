package serg.chuprin.finances.feature.categories.impl.presentation.view

import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import androidx.core.transition.doOnEnd
import androidx.core.transition.doOnStart
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_categories_list.*
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.fragmentArguments
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
        registerRenderer(ZeroDataCellRenderer())
        registerRenderer(ChildCategoryCellRenderer())
        registerRenderer(ParentCategoryCellRenderer())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
            recyclerView.fit { Edge.Bottom }
        }

        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        defaultToolbarSearchImageView.onClick {
            viewModel.dispatchIntent(CategoriesListIntent.ClickOnSearchIcon)
        }

        searchToolbarCloseImageView.onClick {
            viewModel.dispatchIntent(CategoriesListIntent.ClickOnCloseSearchIcon)
        }

        defaultToolbarBackButton.onClick {
            navController.navigateUp()
        }

        cellsAdapter.clickListener = { cell, cellView, _ ->
            if (cell is ParentCategoryCell && cellView.id == R.id.expansionArrowImageView) {
                viewModel.dispatchIntent(
                    CategoriesListIntent.ClickOnParentCategoryExpansionToggle(cell)
                )
            }
        }

        with(viewModel) {
            cellsLiveData(cellsAdapter::setItems)
            searchModeActiveLiveData { isSearchModeActive ->
                showSearchMode(showSearchMode = isSearchModeActive)
            }
        }
    }

    private fun showSearchMode(showSearchMode: Boolean) {
        val transition = TransitionInflater
            .from(context)
            .inflateTransition(R.transition.slide_bottom)
            .setDuration(250L)

        if (showSearchMode) {
            transition.doOnEnd {
                searchToolbarEditText.showKeyboard()
            }
        } else {
            transition.doOnStart {
                searchToolbarEditText.hideKeyboard()
            }
            transition.doOnEnd {
                searchToolbarEditText.doIgnoringChanges {
                    setText(EMPTY_STRING)
                }
            }
        }

        TransitionManager.beginDelayedTransition(viewSwitcher, transition)

        searchToolbarLayout.makeVisibleOrGone(showSearchMode)
        defaultToolbarLayout.makeVisibleOrGone(!showSearchMode)
    }

}