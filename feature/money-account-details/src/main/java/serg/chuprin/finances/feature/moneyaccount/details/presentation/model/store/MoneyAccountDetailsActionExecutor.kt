package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import serg.chuprin.finances.core.api.domain.usecase.MarkMoneyAccountAsFavoriteUseCase
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder.DateTimeFormattingMode
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.cells.DateDividerCell
import serg.chuprin.finances.core.api.presentation.model.cells.ZeroDataCell
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountDetailsScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.core.api.presentation.screen.arguments.TransactionScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.moneyaccount.details.R
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsActionExecutor @Inject constructor(
    private val resourceManger: ResourceManger,
    private val amountFormatter: AmountFormatter,
    private val dateTimeFormatter: DateTimeFormatter,
    private val transactionCellBuilder: TransactionCellBuilder,
    private val screenArguments: MoneyAccountDetailsScreenArguments,
    private val markMoneyAccountAsFavoriteUseCase: MarkMoneyAccountAsFavoriteUseCase
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
                when (val intent = action.intent) {
                    MoneyAccountDetailsIntent.ClickOnFavoriteIcon -> {
                        handleClickOnFavoriteIconIntent(state)
                    }
                    is MoneyAccountDetailsIntent.ClickOnTransactionCell -> {
                        handleClickOnTransactionCellIntent(intent, eventConsumer)
                    }
                    MoneyAccountDetailsIntent.ClickOnEditingButton -> {
                        handleClickOnEditingButtonIntent(eventConsumer)
                    }
                }
            }
        }
    }

    private fun handleClickOnEditingButtonIntent(
        eventConsumer: Consumer<MoneyAccountDetailsEvent>
    ): Flow<MoneyAccountDetailsEffect> {
        return emptyFlowAction {
            val screenArguments = MoneyAccountScreenArguments.Editing(
                moneyAccountId = screenArguments.moneyAccountId,
                transitionName = resourceManger.getString(
                    R.string.transition_name_money_account_editing
                )
            )
            eventConsumer(
                MoneyAccountDetailsEvent.NavigateToMoneyAccountEditingScreen(screenArguments)
            )
        }
    }

    private fun handleClickOnTransactionCellIntent(
        intent: MoneyAccountDetailsIntent.ClickOnTransactionCell,
        eventConsumer: Consumer<MoneyAccountDetailsEvent>
    ): Flow<MoneyAccountDetailsEffect> {
        return emptyFlowAction {
            val transactionCell = intent.transactionCell
            val screenArguments = TransactionScreenArguments.Editing(
                transactionId = transactionCell.transaction.id,
                transitionName = transactionCell.transitionName
            )
            eventConsumer(MoneyAccountDetailsEvent.NavigateToTransactionScreen(screenArguments))
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
        val cells = buildCells(details.transactionsGroupedByDay)
        val formattedBalance = amountFormatter.format(
            round = false,
            amount = details.balance,
            currency = moneyAccount.currency
        )
        return flowOf(
            MoneyAccountDetailsEffect.DetailsFormatted(
                cells = cells,
                moneyAccount = moneyAccount,
                formattedBalance = formattedBalance,
                moneyAccountName = moneyAccount.name,
                isFavorite = moneyAccount.isFavorite
            )
        )
    }

    private fun buildCells(
        transactionsGroupedByDay: TransactionsGroupedByDay
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
                        add(
                            transactionCellBuilder.build(
                                moneyAccount = null,
                                transaction = transaction,
                                categoryWithParent = category,
                                dateTimeFormattingMode = DateTimeFormattingMode.ONLY_TIME
                            )
                        )
                    }
                }
            }
    }

}