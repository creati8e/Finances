package serg.chuprin.finances.feature.onboarding.presentation.model.cells

import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyCell(
    val currencyCode: String,
    val displayName: String
) : BaseCell