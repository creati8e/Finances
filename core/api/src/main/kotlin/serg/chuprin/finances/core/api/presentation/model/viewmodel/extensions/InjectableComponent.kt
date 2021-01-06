package serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
interface InjectableComponent<T> {

    fun inject(where: T)

}