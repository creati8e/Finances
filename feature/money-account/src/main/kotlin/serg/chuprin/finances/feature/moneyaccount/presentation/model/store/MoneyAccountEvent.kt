package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountEvent {

    object CloseScreen : MoneyAccountEvent()

    object ShowAccountDeletionDialog : MoneyAccountEvent()

    class ShowMessage(
        val message: String
    ) : MoneyAccountEvent()

}