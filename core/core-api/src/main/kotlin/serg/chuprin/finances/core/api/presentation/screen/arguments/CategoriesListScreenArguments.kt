package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListScreenArguments : Parcelable {

    /**
     * Allow user to select single category (category picker).
     */
    @Parcelize
    object Picker : CategoriesListScreenArguments()

    /**
     * Mode when user can edit categories or delete them.
     */
    @Parcelize
    object Editing : CategoriesListScreenArguments()

}