plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

dependencies {
    implementation(project(":injector"))
    implementation(project(":core:core-api"))
    implementation(project(":feature:feature-dashboard-setup-api"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region UI.

    implementation(Libraries.COIL)
    implementation(Libraries.FLEXBOX)
    implementation(Libraries.PAGE_INDICATOR)

    // Navigation.
    implementationAll(Libraries.Android.Navigation.DEPENDENCIES)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    implementationAll(Libraries.Adapter.DEPENDENCIES)

    // endregion

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementationAll(Libraries.Dagger.DEPENDENCIES)

    // endregion

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

}