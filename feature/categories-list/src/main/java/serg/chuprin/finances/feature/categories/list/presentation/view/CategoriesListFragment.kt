package serg.chuprin.finances.feature.categories.list.presentation.view

import android.os.Bundle
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import androidx.core.transition.doOnEnd
import androidx.core.transition.doOnStart
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_categories_list.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.renderer.ZeroDataCellRenderer
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.fragmentArguments
import serg.chuprin.finances.feature.categories.list.R
import serg.chuprin.finances.feature.categories.list.di.CategoriesListComponent
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ChildCategoryCell
import serg.chuprin.finances.feature.categories.list.presentation.model.cell.ParentCategoryCell
import serg.chuprin.finances.feature.categories.list.presentation.model.store.CategoriesListEvent
import serg.chuprin.finances.feature.categories.list.presentation.model.store.CategoriesListIntent
import serg.chuprin.finances.feature.categories.list.presentation.view.adapter.diff.CategoriesListCellsAdapterDiffCallback
import serg.chuprin.finances.feature.categories.list.presentation.view.adapter.renderer.ChildCategoryCellRenderer
import serg.chuprin.finances.feature.categories.list.presentation.view.adapter.renderer.ParentCategoryCellRenderer


/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesListFragment : BaseFragment(R.layout.fragment_categories_list) {

    private val viewModel by viewModelFromComponent {
        CategoriesListComponent.get(fragmentArguments(), findComponentDependencies())
    }

    private val cellsAdapter =
        DiffMultiViewAdapter(CategoriesListCellsAdapterDiffCallback()).apply {
            registerRenderer(ZeroDataCellRenderer())
            registerRenderer(ChildCategoryCellRenderer())
            registerRenderer(ParentCategoryCellRenderer())
        }

    private var searchEditTextWatcher: TextWatcher? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top + Edge.Left + Edge.Right }
            recyclerView.fit { Edge.Bottom }
        }

        setupCellsList()

        setToolbarIconsClickListeners()

        hideKeyboardOnScrollInSearchMode()

        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
            searchModeActiveLiveData(::showSearchMode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchToolbarEditText.removeTextChangedListener(searchEditTextWatcher)
    }

    private fun handleEvent(event: CategoriesListEvent) {
        return when (event) {
            is CategoriesListEvent.CloseScreenWithPickerCategory -> {
                setFragmentResult(
                    CategoriesListScreenArguments.Picker.REQUEST_KEY,
                    CategoriesListScreenArguments.Picker.Result(event.categoryId.value).asBundle()
                )
                navController.navigateUp()
                Unit
            }
        }
    }

    private fun setupCellsList() {
        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        cellsAdapter.clickListener = { cell, cellView, _ ->
            if (cell is ParentCategoryCell) {
                if (cellView.id == R.id.expansionArrowImageView) {
                    viewModel.dispatchIntent(
                        CategoriesListIntent.ClickOnParentCategoryExpansionToggle(cell)
                    )
                } else {
                    viewModel.dispatchIntent(CategoriesListIntent.ClickOnCategoryCell(cell))
                }
            } else if (cell is ChildCategoryCell) {
                viewModel.dispatchIntent(CategoriesListIntent.ClickOnCategoryCell(cell))
            }
        }
    }

    private fun setToolbarIconsClickListeners() {
        defaultToolbarSearchImageView.onClick {
            viewModel.dispatchIntent(CategoriesListIntent.ClickOnSearchIcon)
        }

        searchToolbarCloseImageView.onClick {
            viewModel.dispatchIntent(CategoriesListIntent.ClickOnCloseSearchIcon)
        }

        defaultToolbarBackButton.onClick {
            navController.navigateUp()
        }
    }

    private fun hideKeyboardOnScrollInSearchMode() {
        recyclerView.onScrollStateChanged { _, newState ->
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING && searchToolbarLayout.isVisible) {
                searchToolbarEditText.hideKeyboard()
            }
        }
    }

    private fun setOrRemoveQueryListener(isSearchModeActive: Boolean) {
        if (!isSearchModeActive) {
            searchToolbarEditText.removeTextChangedListener(searchEditTextWatcher)
            return
        }
        searchEditTextWatcher = searchToolbarEditText.doAfterTextChanged { editable ->
            if (editable != null && !searchToolbarEditText.shouldIgnoreChanges) {
                viewModel.dispatchIntent(
                    CategoriesListIntent.EnterSearchQuery(editable.toString())
                )
            }
        }
    }

    private fun showSearchMode(showSearchMode: Boolean) {

        setOrRemoveQueryListener(showSearchMode)

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