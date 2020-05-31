package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.domain.usecase.MarkMoneyAccountAsFavoriteUseCase
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.moneyaccount.details.R
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsActionExecutor @Inject constructor(
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val categoryColorFormatter: CategoryColorFormatter,
    private val markMoneyAccountAsFavoriteUseCase: MarkMoneyAccountAsFavoriteUseCase,
    private val transactionCategoryWithParentFormatter: TransactionCategoryWithParentFormatter
) : StoreActionExecutor<MoneyAccountDetailsAction, MoneyAccountDetailsState, MoneyAccountDetailsEffect, MoneyAccountDetailsEvent> {

    override fun invoke(
        action: MoneyAccountDetailsAction,
        state: MoneyAccountDetailsState,
        eventConsumer: Consumer<MoneyAccountDetailsEvent>,
        actionsFlow: Flow<MoneyAccountDetailsAction>
    ): Flow<MoneyAccountDetailsEffect> {
        return when (action) {
            is MoneyAccountDetailsAction.FormatDetails -> {
                handleFormatDetailsAction(action, eventConsumer)
            }
            is MoneyAccountDetailsAction.ExecuteIntent -> {
                when (action.intent) {
                    MoneyAccountDetailsIntent.ClickOnFavoriteIcon -> {
                        handleClickOnFavoriteIconIntent(state)
                    }
                }
            }
        }
    }

    private fun handleClickOnFavoriteIconIntent(
        state: MoneyAccountDetailsState
    ): Flow<MoneyAccountDetailsEffect> {
        return emptyFlowAction {
            val markAsFavorite = state.isFavorite.not()
            markMoneyAccountAsFavoriteUseCase.execute(state.moneyAccount, markAsFavorite)
        }
    }

    private fun handleFormatDetailsAction(
        action: MoneyAccountDetailsAction.FormatDetails,
        eventConsumer: Consumer<MoneyAccountDetailsEvent>
    ): Flow<MoneyAccountDetailsEffect> {
        val details = action.details ?: return emptyFlowAction {
            eventConsumer(MoneyAccountDetailsEvent.CloseScreen)
        }
        val moneyAccount = details.moneyAccount
        val cells = buildCells(moneyAccount.currency, details.transactionsGroupedByDay)
        val formattedBalance = amountFormatter.format(
            round = false,
            amount = details.balance,
            currency = moneyAccount.currency
        )
        val effect = MoneyAccountDetailsEffect.DetailsFormatted(
            cells = cells,
            moneyAccount = moneyAccount,
            formattedBalance = formattedBalance,
            moneyAccountName = moneyAccount.name,
            isFavorite = moneyAccount.isFavorite
        )
        return flowOf(effect)
    }

    private fun buildCells(
        currency: Currency,
        transactionsGroupedByDay: SortedMap<LocalDate, List<Map.Entry<Transaction, TransactionCategoryWithParent?>>>
    ): List<BaseCell> {
        if (transactionsGroupedByDay.isEmpty()) {
            return listOf(
                ZeroDataCell(
                    iconRes = null,
                    contentMessageRes = null,
                    titleRes = R.string.money_account_details_zero_data_title
                )
            )
        }
        return transactionsGroupedByDay.entries
            .fold(mutableListOf()) { cells, (localDate, transactionsWithCategories) ->
                cells.apply {
                    add(
                        DateDividerCell(
                            localDate = localDate,
                            dateFormatted = dateTimeFormatter.formatAsDay(localDate)
                        )
                    )
                    transactionsWithCategories.forEach { (transaction, category) ->
                        val (parentCategoryName, subcategoryName) =
                            transactionCategoryWithParentFormatter.format(category, transaction)
                        add(
                            TransactionCell(
                                transaction = transaction,
                                isIncome = transaction.isIncome,
                                subcategoryName = subcategoryName,
                                parentCategoryName = parentCategoryName,
                                color = categoryColorFormatter.format(category?.category),
                                time = dateTimeFormatter.formatTime(transaction.dateTime),
                                amount = amountFormatter.format(
                                    round = false,
                                    currency = currency,
                                    amount = transaction.amount
                                )
                            )
                        )
                    }
                }
            }
    }

}