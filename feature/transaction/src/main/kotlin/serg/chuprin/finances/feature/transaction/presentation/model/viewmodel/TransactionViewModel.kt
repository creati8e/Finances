package serg.chuprin.finances.feature.transaction.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.presentation.model.viewmodel.BaseStoreViewModel
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionEvent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionIntent
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionState
import serg.chuprin.finances.feature.transaction.presentation.model.store.TransactionStore
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
@ScreenScope
class TransactionViewModel @Inject constructor(
    private val store: TransactionStore
) : BaseStoreViewModel<TransactionIntent>() {

    val currency: Currency?
        get() {
            val moneyAccount = store.state.chosenMoneyAccount.account
            return if (moneyAccount == MoneyAccount.EMPTY) {
                null
            } else {
                moneyAccount.currency
            }
        }

    val currencySymbolLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData(TransactionState::currencySymbol)

    val enteredAmountLiveData: LiveData<BigDecimal> = store
        .stateFlow
        .mapNotNull { state -> state.enteredAmount }
        .asLiveData()

    val transactionDeletionButtonVisibilityLiveDate: LiveData<Boolean> =
        store.observeParticularStateAsLiveData(TransactionState::transactionDeletionButtonIsVisible)

    val chosenMoneyAccountLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenMoneyAccount.formattedName }

    val chosenDateLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenDate.formatted }

    val chosenCategoryLiveData: LiveData<String> =
        store.observeParticularStateAsLiveData { state -> state.chosenCategory.formattedName }

    val isSaveButtonEnabledLiveData: LiveData<Boolean> =
        store.observeParticularStateAsLiveData { state -> state.saveButtonIsEnabled }

    val chosenOperationLiveData: LiveData<TransactionChosenOperation> =
        store.observeParticularStateAsLiveData { state -> state.operation }

    val eventLiveData: LiveData<TransactionEvent> = store.observeEventsAsLiveData()

    init {
        store.start(intentsFlow(), viewModelScope)
    }

}