package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
interface CurrencyChoiceStoreProvider {

    fun provide(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore

}