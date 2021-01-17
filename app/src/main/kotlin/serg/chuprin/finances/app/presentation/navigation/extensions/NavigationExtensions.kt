package serg.chuprin.finances.app.presentation.navigation.extensions

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
fun buildExtrasForSharedElements(
    sharedElementView: Array<out View>
): FragmentNavigator.Extras {
    return FragmentNavigator.Extras.Builder().run {
        sharedElementView.forEach { view ->
            addSharedElement(view, view.transitionName)
        }
        build()
    }
}

fun View.toNavigatorExtras(): FragmentNavigator.Extras {
    return FragmentNavigatorExtras(this to this.transitionName)
}