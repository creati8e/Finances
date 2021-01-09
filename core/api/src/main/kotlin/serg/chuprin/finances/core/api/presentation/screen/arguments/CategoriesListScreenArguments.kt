package serg.chuprin.finances.core.api.presentation.screen.arguments

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.CategoryType

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListScreenArguments : Parcelable {

    /**
     * Allow user to select single category (category picker).
     */
    @Parcelize
    class Picker(
        override val categoryType: CategoryType?
    ) : CategoriesListScreenArguments() {

        companion object {
            const val REQUEST_KEY = "CATEGORIES_LIST_SCREEN_PICKER_REQUEST_KEY"
        }

        @Parcelize
        class Result(
            private val _categoryId: String
        ) : Parcelable {

            companion object {

                fun fromBundle(bundle: Bundle): Result {
                    return Result(requireNotNull(bundle.getString("category_id")))
                }

            }

            val categoryId: Id = Id.existing(_categoryId)

            fun asBundle(): Bundle = bundleOf("category_id" to _categoryId)

        }

    }

    /**
     * Mode when user can edit categories or delete them.
     */
    @Parcelize
    object Editing : CategoriesListScreenArguments()

    open val categoryType: CategoryType? = null

    fun isInPickerMode(): Boolean = this is Picker

}