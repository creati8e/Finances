package serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Sergey Chuprin on 31.01.2019.
 */
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = creator() as T

}