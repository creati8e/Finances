package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
sealed class MoneyAccountScreenArguments(
    open val transitionName: String
) : Parcelable {

    @Parcelize
    class Editing(
        val moneyAccountId: Id,
        override val transitionName: String
    ) : MoneyAccountScreenArguments(transitionName)

    @Parcelize
    class Creation(
        override val transitionName: String
    ) : MoneyAccountScreenArguments(transitionName)

}