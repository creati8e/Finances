package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Reusable
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.impl.presentation.builder.TransactionCellBuilderImpl
import serg.chuprin.finances.core.impl.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilderImpl
import serg.chuprin.finances.core.impl.presentation.model.builder.TransitionNameBuilderImpl

/**
 * Created by Sergey Chuprin on 23.04.2020.
 */
@Module
internal interface CoreBuildersModule {

    @[Binds Reusable]
    fun bindDataPeriodTypePopupMenuCellsBuilder(
        impl: DataPeriodTypePopupMenuCellsBuilderImpl
    ): DataPeriodTypePopupMenuCellsBuilder

    @[Binds Reusable]
    fun bindTransitionNameBuilder(impl: TransitionNameBuilderImpl): TransitionNameBuilder

    @[Binds Reusable]
    fun bindTransactionCellBuilder(impl: TransactionCellBuilderImpl): TransactionCellBuilder

}