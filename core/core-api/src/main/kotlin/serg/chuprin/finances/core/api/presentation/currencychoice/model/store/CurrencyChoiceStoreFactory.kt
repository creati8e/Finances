package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
interface CurrencyChoiceStoreFactory {

    fun create(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore

}