plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

dependencies {
    implementation(project(":core:api"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)
    implementation(Libraries.Coroutines.Bindings)

    // region UI.

    implementation(Libraries.COIL)

    // Navigation.
    implementation(Libraries.Android.Navigation)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.TRANSITION)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    implementation(Libraries.Adapter)

    // endregion

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementation(Libraries.Dagger.LIBRARY)

    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    testImplementation(project(":core:test"))

}