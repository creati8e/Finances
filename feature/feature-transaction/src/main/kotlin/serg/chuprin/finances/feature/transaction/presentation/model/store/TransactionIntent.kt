package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.feature.transaction.domain.model.TransactionChosenOperation

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionIntent {

    object ClickOnCategory : TransactionIntent()

    object ClickOnSaveButton : TransactionIntent()

    object ClickOnCloseButton : TransactionIntent()

    class EnterAmount(
        val amount: String
    ) : TransactionIntent()

    class ChooseCategory(
        val categoryId: Id
    ) : TransactionIntent()

    class ClickOnOperationType(
        val operation: TransactionChosenOperation
    ) : TransactionIntent()

}