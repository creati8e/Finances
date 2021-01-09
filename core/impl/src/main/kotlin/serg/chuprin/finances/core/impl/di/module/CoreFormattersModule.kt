package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Reusable
import serg.chuprin.finances.core.api.presentation.formatter.*
import serg.chuprin.finances.core.impl.presentation.model.formatter.*

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
internal interface CoreFormattersModule {

    @[Binds Reusable]
    fun bindDateTimeFormatter(impl: DateTimeFormatterImpl): DateTimeFormatter

    @[Binds Reusable]
    fun bindCategoryWithParentFormatter(
        impl: CategoryWithParentFormatterImpl
    ): CategoryWithParentFormatter

    @[Binds Reusable]
    fun bindAmountFormatter(impl: AmountFormatterImpl): AmountFormatter

    @[Binds Reusable]
    fun bindDataPeriodFormatter(impl: DataPeriodFormatterImpl): DataPeriodFormatter

    @[Binds Reusable]
    fun bindCategoryColorFormatter(impl: CategoryColorFormatterImpl): CategoryColorFormatter

}