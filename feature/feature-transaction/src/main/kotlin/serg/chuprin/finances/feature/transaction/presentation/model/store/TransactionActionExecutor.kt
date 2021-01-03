package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.feature.transaction.R
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate
import serg.chuprin.finances.feature.transaction.presentation.model.formatter.TransactionChosenDateFormatter
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionActionExecutor @Inject constructor(
    private val resourceManger: ResourceManger,
    private val chosenDateFormatter: TransactionChosenDateFormatter
) : StoreActionExecutor<TransactionAction, TransactionState, TransactionEffect, TransactionEvent> {

    override fun invoke(
        action: TransactionAction,
        state: TransactionState,
        eventConsumer: Consumer<TransactionEvent>,
        actionsFlow: Flow<TransactionAction>
    ): Flow<TransactionEffect> {
        return when (action) {
            is TransactionAction.ExecuteIntent -> TODO()
            is TransactionAction.FormatInitialState -> {
                handleFormatInitialStateAction(action)
            }
        }
    }

    private fun handleFormatInitialStateAction(
        action: TransactionAction.FormatInitialState
    ): Flow<TransactionEffect> {
        return flowOf(
            TransactionEffect.DateChanged(buildChosenDate(action.date)),
            TransactionEffect.CategoryChanged(buildChosenCategory(action.category))
        )
    }

    private fun buildChosenCategory(category: TransactionCategory?): TransactionChosenCategory {
        val formattedName = category?.name ?: resourceManger.getString(R.string.no_category)
        return TransactionChosenCategory(formattedName, category)
    }

    private fun buildChosenDate(date: LocalDate): TransactionChosenDate {
        return TransactionChosenDate(chosenDateFormatter.format(date), date)
    }

}