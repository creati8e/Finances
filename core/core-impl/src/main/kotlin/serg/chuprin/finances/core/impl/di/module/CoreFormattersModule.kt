package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import dagger.Reusable
import serg.chuprin.finances.core.api.presentation.formatter.DataPeriodFormatter
import serg.chuprin.finances.core.api.presentation.model.formatter.AmountFormatter
import serg.chuprin.finances.core.impl.presentation.model.formatter.AmountFormatterImpl
import serg.chuprin.finances.core.impl.presentation.model.formatter.DataPeriodFormatterImpl

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
internal abstract class CoreFormattersModule {

    @Binds
    @Reusable
    abstract fun bindAmountFormatter(impl: AmountFormatterImpl): AmountFormatter

    @Binds
    abstract fun bindDataPeriodFormatter(impl: DataPeriodFormatterImpl): DataPeriodFormatter

}