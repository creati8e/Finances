package serg.chuprin.finances.feature.dashboard.presentation.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import serg.chuprin.finances.feature.dashboard.domain.usecase.ObserveLastUserTransactionsUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardViewModel @Inject constructor(
    observeLastTransactionsUseCase: ObserveLastUserTransactionsUseCase
) : ViewModel() {

    val lastTransactionsLiveData = observeLastTransactionsUseCase.execute().asLiveData()

}