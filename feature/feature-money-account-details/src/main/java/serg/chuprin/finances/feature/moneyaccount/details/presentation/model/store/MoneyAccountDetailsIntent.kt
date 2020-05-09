package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
sealed class MoneyAccountDetailsIntent {

    object ClickOnFavoriteIcon : MoneyAccountDetailsIntent()

}