package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Reusable
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.impl.presentation.model.formatter.AmountFormatterImpl
import serg.chuprin.finances.core.impl.presentation.model.formatter.DataPeriodFormatterImpl
import serg.chuprin.finances.core.impl.presentation.model.formatter.DateTimeFormatterImpl
import serg.chuprin.finances.core.impl.presentation.model.formatter.TransactionCategoryWithParentFormatterImpl

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
internal interface CoreFormattersModule {

    @[Binds Reusable]
    fun bindDateTimeFormatter(impl: DateTimeFormatterImpl): DateTimeFormatter

    @[Binds Reusable]
    fun bindTransactionCategoryWithParentFormatter(
        impl: TransactionCategoryWithParentFormatterImpl
    ): TransactionCategoryWithParentFormatter

    @[Binds Reusable]
    fun bindAmountFormatter(impl: AmountFormatterImpl): AmountFormatter

    @[Binds Reusable]
    fun bindDataPeriodFormatter(impl: DataPeriodFormatterImpl): DataPeriodFormatter

}