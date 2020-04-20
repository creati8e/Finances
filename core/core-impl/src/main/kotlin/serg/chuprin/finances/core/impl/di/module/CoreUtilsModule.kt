package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.impl.presentation.model.parser.AmountParserImpl

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
@Module
internal interface CoreUtilsModule {

    @Binds
    fun bindAmountParser(impl: AmountParserImpl): AmountParser

}