package serg.chuprin.finances.core.api.presentation.view

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import serg.chuprin.finances.core.api.presentation.navigation.RootNavigator
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar

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

    protected var menu: Menu? = null

    private var menuConfig: MenuConfig? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuConfig = createMenu()
        if (menuConfig != null) {
            setHasOptionsMenu(true)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuConfig?.toolbarProvider?.invoke()?.let { toolbar ->
            setupToolbar(toolbar) {
                title = EMPTY_STRING
            }
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        menu = null
        menuConfig?.toolbarProvider = null
        menuConfig = null
    }

    // region Menu.

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuConfig?.menuRes?.forEach { menuRes -> inflater.inflate(menuRes, menu) }
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuConfig?.onMenuItemClick?.forEach { it.invoke(item) }
        return super.onOptionsItemSelected(item)
    }

    @CallSuper
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menuConfig?.onPrepareMenu?.forEach { it.invoke(menu) }
        this.menu = menu
    }

    // endregion

    protected open fun createMenu(): MenuConfig? = null

    protected operator fun <T> LiveData<T>.invoke(consumer: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer(consumer))
    }

    protected operator fun LiveData<Unit>.invoke(consumer: () -> Unit) {
        observe(viewLifecycleOwner, { consumer() })
    }

}