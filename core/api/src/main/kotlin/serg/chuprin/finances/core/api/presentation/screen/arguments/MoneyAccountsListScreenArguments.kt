package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
sealed class MoneyAccountsListScreenArguments : Parcelable {

    /**
     * Allow user to select single money account.
     */
    @Parcelize
    object Picker : MoneyAccountsListScreenArguments() {

        const val REQUEST_KEY = "MONEY_ACCOUNTS_LIST_SCREEN_PICKER_REQUEST_KEY"

        @Parcelize
        class Result(
            private val _moneyAccountId: String
        ) : Parcelable {

            companion object {

                fun fromBundle(bundle: Bundle): Result {
                    return Result(requireNotNull(bundle.getString("money_account_id")))
                }

            }

            val moneyAccountId: Id = Id.existing(_moneyAccountId)

            fun asBundle(): Bundle = bundleOf("money_account_id" to _moneyAccountId)

        }

    }

    /**
     * Mode when user can edit money accounts or delete them.
     */
    @Parcelize
    object Editing : MoneyAccountsListScreenArguments()

    fun isInPickerMode(): Boolean = this is Picker

}