package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
sealed class MoneyAccountDetailsIntent {

    object ClickOnFavoriteIcon : MoneyAccountDetailsIntent()

    object ClickOnEditingButton : MoneyAccountDetailsIntent()

    class ClickOnTransactionCell(
        val transactionCell: TransactionCell
    ) : MoneyAccountDetailsIntent()

}