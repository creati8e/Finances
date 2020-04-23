package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Reusable
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.impl.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilderImpl

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
@Module
interface CoreBuildersModule {

    @[Binds Reusable]
    fun bindDataPeriodTypePopupMenuCellsBuilder(
        impl: DataPeriodTypePopupMenuCellsBuilderImpl
    ): DataPeriodTypePopupMenuCellsBuilder

}