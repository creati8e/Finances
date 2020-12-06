plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

dependencies {
    implementation(project(":core:core-api"))
    implementation(project(":injector"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region UI.

    implementation(Libraries.COIL)
    implementationAll(Libraries.Adapter.DEPENDENCIES)

    // Navigation.
    implementationAll(Libraries.Android.Navigation.DEPENDENCIES)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.VIEWPAGER)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.TRANSITION)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    // endregion

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementationAll(Libraries.Dagger.DEPENDENCIES)

    // endregion

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

}