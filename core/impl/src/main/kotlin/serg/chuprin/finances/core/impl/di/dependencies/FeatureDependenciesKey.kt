package serg.chuprin.finances.core.impl.di.dependencies

import dagger.MapKey
import dagger.Module
import dagger.multibindings.Multibinds
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.di.dependencies.FeatureDependenciesProvider
import kotlin.reflect.KClass

/**
 * Created by Sergey Chuprin on 16.01.2021.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class FeatureDependenciesKey(val value: KClass<out FeatureDependencies>)

@Module
abstract class DummyComponentDependenciesModule private constructor() {

    @Multibinds
    abstract fun componentDependencies(): FeatureDependenciesProvider

}