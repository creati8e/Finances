package serg.chuprin.finances.core.api.presentation.currencychoice.model.store

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
interface CurrencyChoiceStoreProvider {

    /**
     * @return [CurrencyChoiceStore] with provided [bootstrapper].
     * This allows to create store with different bootstrapping
     * logic depending on where store is used.
     */
    fun provide(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore

}