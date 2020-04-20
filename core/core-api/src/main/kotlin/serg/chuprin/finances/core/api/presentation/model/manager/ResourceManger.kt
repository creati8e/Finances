package serg.chuprin.finances.core.api.presentation.model.manager

import androidx.annotation.*

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface ResourceManger {

    @ColorInt
    fun getColor(@ColorRes colorRes: Int): Int

    fun getString(@StringRes stringRes: Int): String

    fun getDimenInt(@DimenRes dimenRes: Int): Int

    @ColorInt
    fun getThemeColor(@AttrRes colorAttrRes: Int): Int

    fun getString(@StringRes stringRes: Int, vararg arrayOfAnys: Any): String

    fun getPlurals(@PluralsRes pluralsRes: Int, count: Int): String

}