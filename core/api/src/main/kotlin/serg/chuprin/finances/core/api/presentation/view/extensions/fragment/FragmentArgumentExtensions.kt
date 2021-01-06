package serg.chuprin.finances.core.api.presentation.view.extensions.fragment

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

/**
 * Created by Sergey Chuprin on 05.02.2019.
 *
 * Convenient methods for retrieving fragment arguments.
 * Example: val dialogsArguments by arguments<SingleChoiceDialogArguments> { arguments }
 * or val dialogsArguments by arguments<SingleChoiceDialogArguments>()
 * [ARGUMENT] - argument class.
 */
inline fun <reified ARGUMENT : Parcelable> Fragment.arguments(): Lazy<ARGUMENT> = lazyArguments()

inline fun <reified ARGUMENT : Parcelable> Fragment.lazyArguments(
    crossinline bundleProvider: () -> Bundle? = { arguments }
): Lazy<ARGUMENT> {
    return lazy(LazyThreadSafetyMode.NONE) {
        requireNotNull(bundleProvider()?.getParcelable<ARGUMENT>(getArgumentKey())) {
            "${ARGUMENT::class.java.name} not found in bundle"
        }
    }
}

inline fun <reified ARGUMENT : Parcelable> Fragment.fragmentArguments(
    crossinline bundleProvider: () -> Bundle? = { arguments }
): ARGUMENT {
    return requireNotNull(bundleProvider()?.getParcelable(getArgumentKey())) {
        "${ARGUMENT::class.java.name} not found in bundle"
    }
}

/**
 * [T] - class of fragment where this bundle will be passed.
 */
inline fun <reified T : Fragment> Parcelable.toBundle(): Bundle {
    return bundleOf(T::class.getArgumentKey() to this)
}

inline fun <reified T : KClass<out Fragment>> T.getArgumentKey(): String {
    // This is strange, but inlining local variable leads to app crash.
    val className = java.name
    return "${className}_FRAGMENT_ARG_KEY"
}

inline fun <reified T : Fragment> T.getArgumentKey(): String = this::class.getArgumentKey()

inline fun <reified T : KClass<out Fragment>> T.buildTag(): String {
    return "${java.name}_$FRAGMENT_TAG"
}