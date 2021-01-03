package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
@Parcelize
class TransactionScreenArguments(
    val transitionName: String
) : Parcelable