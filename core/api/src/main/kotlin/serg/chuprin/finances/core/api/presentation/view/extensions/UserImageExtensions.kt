package serg.chuprin.finances.core.api.presentation.view.extensions

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import serg.chuprin.finances.core.api.R

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
fun ImageView.loadImage(url: String) {
    load(url) {
        val radius = context.getDimenDpFloat(R.dimen.radiusCornerDefault)
        transformations(RoundedCornersTransformation(radius))
        error(R.drawable.ic_user_photo_placeholder)
        placeholder(R.drawable.ic_user_photo_placeholder)
    }
}