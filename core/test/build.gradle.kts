plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {

    implementation(project(":core:api"))
    implementation(project(":core:mvi"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)
    implementation(Libraries.JAVAX_ANNOTATIONS)

    // region UI.

    implementation(Libraries.Adapter)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)

    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    // Timber.
    implementation(Libraries.TIMBER)

    implementation(Libraries.Tests.MOCKK)
    implementation(Libraries.Tests.STRIKT)
    implementation(Libraries.Tests.FILE_PEEK)
    implementation(Libraries.Tests.ASSERTIONS)

}