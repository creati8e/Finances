package serg.chuprin.finances.app.di.feature

import dagger.MapKey
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import kotlin.reflect.KClass

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class FeatureDependenciesKey(val value: KClass<out FeatureDependencies>)