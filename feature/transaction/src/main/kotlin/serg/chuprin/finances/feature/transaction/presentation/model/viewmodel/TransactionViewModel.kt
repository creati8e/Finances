package serg.chuprin.finances.feature.transaction.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.take
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
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

    val currency: Currency
        get() = store.state.chosenMoneyAccount.account.currency

    /**
     * If amount exists (when editing existing transaction for example), we need to set it only once.
     */
    val enteredAmountLiveData: LiveData<BigDecimal?> = store
        .stateFlow
        .mapNotNull { state -> state.enteredAmount }
        .take(1)
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