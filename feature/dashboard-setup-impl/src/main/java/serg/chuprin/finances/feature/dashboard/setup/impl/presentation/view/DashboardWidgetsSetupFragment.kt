package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_dashboard_widgets_setup.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.MenuConfig
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar
import serg.chuprin.finances.core.api.presentation.view.menuConfig
import serg.chuprin.finances.core.api.presentation.view.setSharedElementTransitions
import serg.chuprin.finances.feature.dashboard.setup.impl.R
import serg.chuprin.finances.feature.dashboard.setup.impl.di.DashboardWidgetsSetupComponent
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.cells.DraggableDashboardWidgetCell
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupEvent
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.model.store.DashboardWidgetsSetupIntent
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view.adapter.DragTouchCallback
import serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view.adapter.renderer.DraggableDashboardWidgetCellRenderer

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class DashboardWidgetsSetupFragment : BaseFragment(R.layout.fragment_dashboard_widgets_setup) {

    private val viewModel by viewModelFromComponent { component }

    private val component by component {
        DashboardWidgetsSetupComponent.get(findComponentDependencies())
    }

    private val widgetCellsAdapter = DiffMultiViewAdapter(
        DiffCallback<DraggableDashboardWidgetCell>()
    ).apply {
        registerRenderer(DraggableDashboardWidgetCellRenderer(::handleTagFieldCheckedChanged))
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
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        setupRecyclerView()

        with(viewModel) {
            cellsLiveData { (cells, scrollToMovedCell) ->
                if (scrollToMovedCell) {
                    updateList(cells)
                } else {
                    widgetCellsAdapter.setItems(cells)
                }
            }
            eventsLiveData(::handleEvent)
            saveButtonEnabledLiveData { enabled ->
                menu?.setSavingMenuItemEnabled(enabled)
            }
        }
    }

    override fun createMenu(): MenuConfig {
        return menuConfig {
            addMenu(R.menu.menu_dashboard_widgets_setup)
            addMenuItemListener { menuItem ->
                if (menuItem.itemId == R.id.menu_action_save) {
                    viewModel.dispatchIntent(DashboardWidgetsSetupIntent.ClickOnSaveIcon)
                }
            }
            addPrepareMenuListener { menu ->
                menu.setSavingMenuItemEnabled(viewModel.saveButtonEnabled)
            }
        }
    }

    private fun handleEvent(event: DashboardWidgetsSetupEvent) {
        return when (event) {
            DashboardWidgetsSetupEvent.CloseScreen -> {
                navController.navigateUp()
                Unit
            }
        }
    }

    private fun updateList(cells: List<DraggableDashboardWidgetCell>) {
        val lm = recyclerView.layoutManager as LinearLayoutManager
        val firstPos = lm.findFirstCompletelyVisibleItemPosition()

        val offsetTop = if (firstPos >= 0) {
            val firstView = lm.findViewByPosition(firstPos)
            if (firstView == null) {
                0
            } else {
                lm.getDecoratedTop(firstView) - lm.getTopDecorationHeight(firstView)
            }
        } else {
            0
        }

        widgetCellsAdapter.setItems(cells) {
            if (firstPos >= 0) {
                lm.scrollToPositionWithOffset(firstPos, offsetTop)
            }
        }
    }

    private fun setupRecyclerView() {
        with(recyclerView) {
            adapter = widgetCellsAdapter
            layoutManager = LinearLayoutManager(context)

            ItemTouchHelper(
                DragTouchCallback(
                    onCellMoved = { fromPosition, toPosition ->
                        viewModel.dispatchIntent(
                            DashboardWidgetsSetupIntent.MoveWidget(
                                toPosition = toPosition,
                                fromPosition = fromPosition
                            )
                        )
                    },
                    onCellDropped = {
                        viewModel.dispatchIntent(DashboardWidgetsSetupIntent.ResortWidgets)
                    }
                )
            ).attachToRecyclerView(this)
        }
    }

    private fun handleTagFieldCheckedChanged(adapterPosition: Int) {
        val cell =
            widgetCellsAdapter.getItem(adapterPosition) as? DraggableDashboardWidgetCell ?: return
        viewModel.dispatchIntent(DashboardWidgetsSetupIntent.ToggleWidget(cell))
    }

    private fun Menu.setSavingMenuItemEnabled(isEnabled: Boolean) {
        with(findItem(R.id.menu_action_save)) {
            this.isEnabled = isEnabled
            icon.mutate().alpha = if (isEnabled) 255 else 135
        }
    }

}