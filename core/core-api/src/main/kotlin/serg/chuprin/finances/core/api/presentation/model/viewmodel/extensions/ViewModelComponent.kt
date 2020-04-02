package serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions

import androidx.lifecycle.ViewModel

/**
 * Created by Sergey Chuprin on 18.12.2019.
 */
interface ViewModelComponent<VM : ViewModel> {

    val viewModel: VM

}