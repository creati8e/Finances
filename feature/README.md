# Multi-module architecture

In general there are 2 gradle module types existing: core modules and feature
modules.

#### Module dependency rules
Core modules can depends only on other core modules.
* Feature modules can depends only on core modules, or apis of other
  feature modules.

#### Api and Implementation

If feature module exposes some functionality to other features, it
should be split to api and implementation (feature-api and feature-impl
modules).

#### DI for feature modules

Feature dependencies are satisfied via **application**. Application contains
Dagger component with a map of all feature dependencies (dagger multibindings).

Each feature module describes their dependencies in interface called
**MyFeatureDependencies**.  
This interface should be extended from **FeatureDependencies**.

```kotlin
interface AuthorizationDependencies : FeatureDependencies {
    val authorizationGateway: AuthorizationGateway
    val onboardingRepository: OnboardingRepository
    val authorizationNavigation: AuthorizationNavigation
}
```

In application module a Dagger component should be created for feature
dependencies

```kotlin
@Component(
    dependencies = [
        AppNavigationProvider::class, 
        CoreDependenciesProvider::class
    ]
)
internal interface AuthorizationDependenciesComponent : AuthorizationDependencies
```


Then it should be added to feature dependencies map

```kotlin
@Module
object FeatureDependenciesModule {

    @[Provides IntoMap FeatureDependenciesKey(AuthorizationDependencies::class)]
    fun provideAuthorizationDependencies(
        coreDependencies: CoreDependenciesProvider,
        navigationProvider: AppNavigationProvider
    ): FeatureDependencies {
        return DaggerAuthorizationDependenciesComponent
            .builder()
            .coreDependenciesProvider(coreDependencies)
            .appNavigationProvider(navigationProvider)
            .build()
    }

}
```

Dagger component in feature module:

```kotlin
@[ScreenScope Component(dependencies = [AuthorizationDependencies::class])]
interface AuthorizationComponent {

    companion object {

        fun get(dependencies: AuthorizationDependencies): AuthorizationComponent {
            return DaggerAuthorizationComponent
                .builder()
                .authorizationDependencies(dependencies)
                .build()
        }

    }

}
```

To get feature dependencies, **Fragment.findComponentDependencies<T>()**
method exists.

```kotlin
findComponentDependencies<AuthorizationDependencies>()
```


#### Multi-module navigation

Each feature module contains **MyFeatureNavigation** interface with
available navigation actions declared.

```kotlin
interface AuthorizationNavigation {
    fun navigateToAuthorizedGraph(navController: NavController)
}
```

All such interface implementations are stored in application module.
