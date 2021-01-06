package serg.chuprin.finances.core.impl.presentation.model.manager

import android.content.Context
import androidx.annotation.*
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.getDimenDpInt
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
internal class ResourceManagerImpl @Inject constructor(
    private val context: Context
) : ResourceManger {

    @ColorInt
    override fun getColor(@ColorRes colorRes: Int): Int = context.getColorInt(colorRes)

    override fun getString(@StringRes stringRes: Int): String = context.getString(stringRes)

    override fun getDimenInt(@DimenRes dimenRes: Int): Int {
        return context.getDimenDpInt(dimenRes)
    }

    override fun getString(@StringRes stringRes: Int, vararg arrayOfAny: Any): String {
        return context.getString(stringRes, *arrayOfAny)
    }

    override fun getPlurals(@PluralsRes pluralsRes: Int, count: Int): String {
        return context.resources.getQuantityString(pluralsRes, count, count)
    }

}