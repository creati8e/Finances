package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListScreenArguments : Parcelable {

    /**
     * Selection is not available at all.
     */
    @Parcelize
    object Disabled : CategoriesListScreenArguments()

    /**
     * Allow user to select single category (category picker).
     */
    @Parcelize
    object SelectSingle : CategoriesListScreenArguments()

    /**
     * Mode when user can edit categories by selecting multiple categories or deleting it.
     */
    @Parcelize
    class MultiSelection(
        val isDeletionAvailable: Boolean
    ) : CategoriesListScreenArguments()

}