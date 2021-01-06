package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.core.api.presentation.model.DataPeriodUi

/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
sealed class TransactionsReportScreenArguments(
    open val transitionName: String,
    open val dataPeriodUi: DataPeriodUi
) : Parcelable {

    @Parcelize
    class Transactions(
        override val transitionName: String,
        override val dataPeriodUi: DataPeriodUi,
        val transactionType: PlainTransactionType
    ) : TransactionsReportScreenArguments(transitionName, dataPeriodUi)

    @Parcelize
    class AllTransactions(
        override val transitionName: String,
        override val dataPeriodUi: DataPeriodUi
    ) : TransactionsReportScreenArguments(transitionName, dataPeriodUi)

    sealed class Category(
        override val transitionName: String,
        override val dataPeriodUi: DataPeriodUi,
        open val transactionType: PlainTransactionType
    ) : TransactionsReportScreenArguments(transitionName, dataPeriodUi) {

        @Parcelize
        class Other(
            override val transitionName: String,
            override val dataPeriodUi: DataPeriodUi,
            override val transactionType: PlainTransactionType,
            val categoryIds: ArrayList<String>,
        ) : Category(transitionName, dataPeriodUi, transactionType)

        @Parcelize
        class Concrete(
            override val transitionName: String,
            override val dataPeriodUi: DataPeriodUi,
            override val transactionType: PlainTransactionType,
            val categoryId: Id
        ) : Category(transitionName, dataPeriodUi, transactionType)

    }

}