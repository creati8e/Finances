package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
sealed class MoneyAccountIntent {

    object BackPress : MoneyAccountIntent()

    object ClickOnSaveButton : MoneyAccountIntent()

    object ClickOnDeleteButton : MoneyAccountIntent()

    object ClickOnConfirmAccountDeletion : MoneyAccountIntent()

    class EnterAccountName(
        val accountName: String
    ) : MoneyAccountIntent()

    class EnterBalance(
        val balance: String
    ) : MoneyAccountIntent()

}