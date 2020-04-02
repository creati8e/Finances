package serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Sergey Chuprin on 31.01.2019.
 */
inline fun <reified T : ViewModel> Fragment.buildViewModelWithFactory(
    factory: BaseViewModelFactory<T>
): T {
    return ViewModelProvider(this, factory).get(T::class.java)
}

inline fun <reified T : ViewModel> AppCompatActivity.buildViewModelWithFactory(
    factory: BaseViewModelFactory<T>
): T {
    return ViewModelProvider(this, factory).get(T::class.java)
}

fun <T> Fragment.component(creator: () -> T): Lazy<T> {
    return lazy {
        val factory = BaseViewModelFactory { ComponentViewModel(creator()) }
        buildViewModelWithFactory(factory).component
    }
}

fun <T> FragmentActivity.activityComponent(creator: () -> T): Lazy<T> {
    return lazy {
        val factory = BaseViewModelFactory { ComponentViewModel(creator()) }
        (this as AppCompatActivity).buildViewModelWithFactory(factory).component
    }
}

inline fun <reified VM : ViewModel> FragmentActivity.viewModelFromComponent(
    crossinline componentProvider: () -> ViewModelComponent<VM>
): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, BaseViewModelFactory { componentProvider().viewModel })
            .get(VM::class.java)
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelFromComponent(
    crossinline componentProvider: () -> ViewModelComponent<VM>
): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, BaseViewModelFactory { componentProvider().viewModel })
            .get(VM::class.java)
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelFromProvider(
    crossinline provider: () -> VM
): Lazy<VM> {
    return lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, BaseViewModelFactory { provider() })
            .get(VM::class.java)
    }
}