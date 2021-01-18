package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountCreationEvent {

    object CloseScreen : MoneyAccountCreationEvent()

    object ShowAccountDeletionDialog : MoneyAccountCreationEvent()

    class ShowMessage(
        val message: String
    ) : MoneyAccountCreationEvent()

}