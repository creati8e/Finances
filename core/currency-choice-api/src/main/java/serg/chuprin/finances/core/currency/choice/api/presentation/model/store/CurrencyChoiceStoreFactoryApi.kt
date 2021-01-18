package serg.chuprin.finances.core.currency.choice.api.presentation.model.store

/**
 * Created by Sergey Chuprin on 09.06.2020.
 */
interface CurrencyChoiceStoreFactoryApi {

    /**
     * @return [CurrencyChoiceStore] with provided [bootstrapper].
     * This allows to create store with different bootstrapping
     * logic depending on where store is used.
     */
    fun create(bootstrapper: CurrencyChoiceStoreBootstrapper): CurrencyChoiceStore

}