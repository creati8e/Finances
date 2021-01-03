package serg.chuprin.finances.feature.transaction.presentation.view.controller

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.material.TabLayoutSelectionEvent
import reactivecircus.flowbinding.material.tabSelectionEvents
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.view.extensions.doIgnoringChanges
import serg.chuprin.finances.core.api.presentation.view.extensions.shouldIgnoreChanges
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
class TransactionOperationTabsController {

    private companion object {
        private val operationTabPositions = mapOf<Int, TransactionChosenOperation>(
            0 to TransactionChosenOperation.Plain(PlainTransactionType.INCOME),
            1 to TransactionChosenOperation.Plain(PlainTransactionType.EXPENSE),
        )
    }

    fun listenTabChanges(
        tabLayout: TabLayout,
        lifecycleScope: LifecycleCoroutineScope,
        onTabSelected: (TransactionChosenOperation) -> Unit
    ) {
        tabLayout
            .tabSelectionEvents()
            // Skip initial event.
            .drop(1)
            .filterNot { event -> event.tabLayout.shouldIgnoreChanges }
            .filterIsInstance<TabLayoutSelectionEvent.TabSelected>()
            .distinctUntilChangedBy { selected -> selected.tab.position }
            .onEach { event ->
                onTabSelected(operationTabPositions.getValue(event.tab.position))
            }
            .launchIn(lifecycleScope)
    }

    fun selectTabForOperation(tabLayout: TabLayout, operation: TransactionChosenOperation) {
        val tabPosition = operationTabPositions.entries.first { it.value == operation }.key
        if (tabPosition != tabLayout.selectedTabPosition) {
            tabLayout.doIgnoringChanges {
                selectTab(tabLayout.getTabAt(tabPosition), true)
            }
        }
    }

}