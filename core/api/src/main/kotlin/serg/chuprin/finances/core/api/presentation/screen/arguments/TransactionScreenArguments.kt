package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
sealed class TransactionScreenArguments(
    open val transitionName: String
) : Parcelable {

    @Parcelize
    class Editing(
        val transactionId: Id,
        override val transitionName: String
    ) : TransactionScreenArguments(transitionName)

    @Parcelize
    class Creation(
        override val transitionName: String
    ) : TransactionScreenArguments(transitionName)

}