package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.DataPeriodUi

/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
@Parcelize
data class TransactionsReportScreenArguments(
    val transitionName: String,
    val dataPeriodUi: DataPeriodUi? = null,
    val categoryIds: Set<String?> = emptySet(),
    val plainTransactionType: PlainTransactionType? = null
) : Parcelable