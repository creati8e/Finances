plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

dependencies {
    implementation(project(":core:api"))
    implementation(project(":core:currency-choice-api"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region UI.

    implementation(Libraries.COIL)
    implementation(Libraries.Adapter)

    // Navigation.
    implementation(Libraries.Android.Navigation)

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
    implementation(Libraries.Dagger.LIBRARY)

    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    testImplementation(project(":core:test"))

}