package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
sealed class MoneyAccountDetailsEffect {

    class DetailsFormatted(
        val isFavorite: Boolean,
        val cells: List<BaseCell>,
        val moneyAccountName: String,
        val formattedBalance: String,
        val moneyAccount: MoneyAccount
    ) : MoneyAccountDetailsEffect()

}