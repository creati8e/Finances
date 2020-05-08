package serg.chuprin.finances.feature.moneyaccount.details.presentation.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 08.05.2020.
 */
@Parcelize
class MoneyAccountDetailsScreenArguments(
    val moneyAccountId: Id,
    val transitionName: String
) : Parcelable