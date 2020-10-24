package serg.chuprin.finances.core.api.presentation.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import serg.chuprin.finances.core.api.presentation.navigation.RootNavigator

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
abstract class BaseFragment : Fragment {

    @Suppress("unused")
    constructor() : super()

    @Suppress("unused")
    constructor(layoutRes: Int) : super(layoutRes)

    protected val navController: NavController
        get() = findNavController()

    protected val rootNavigationController: NavController
        get() = (requireActivity() as RootNavigator).navController

    protected operator fun <T> LiveData<T>.invoke(consumer: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(consumer))
    }

    protected operator fun LiveData<Unit>.invoke(consumer: () -> Unit) {
        observe(viewLifecycleOwner, Observer { consumer() })
    }

}