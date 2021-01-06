package serg.chuprin.finances.core.api.presentation.view.dialog.info

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Sergey Chuprin on 21.07.2020.
 */
@Parcelize
class InfoDialogArguments(
    // Pass null if you don't want to show title.
    val title: String?,
    val message: String,
    // Pass null if you don't want to show positive btn.
    val positiveText: String?,
    // Pass null if you don't want to show negative btn.
    val negativeText: String?,
    val callbackRequestCode: Int,
    // Flag that indicates whether dialog will close when touch outside or not.
    val isCanceledOnTouchOutside: Boolean = true,
    // Flag that indicated whether dialog will cancel when the back button is pressed or not.
    val isCancellableOnBackPress: Boolean = true
) : Parcelable