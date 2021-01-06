plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

dependencies {

    api(project(":core:mvi"))
    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)
    implementation(Libraries.JAVAX_ANNOTATIONS)

    // region UI.

    implementation(Libraries.COIL)
    implementation(Libraries.Adapter)

    implementation(Libraries.Coroutines.Bindings)

    // Navigation.
    implementation(Libraries.Android.Navigation)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.TRANSITION)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    // Timber.
    api(Libraries.TIMBER)

}