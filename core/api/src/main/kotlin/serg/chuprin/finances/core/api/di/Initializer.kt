package serg.chuprin.finances.core.api.di

import android.app.Application

/**
 * Created by Sergey Chuprin on 14.04.2020.
 */
interface Initializer {

    fun initialize(application: Application)

}