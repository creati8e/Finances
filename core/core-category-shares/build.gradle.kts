plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

dependencies {
    implementation(Libraries.KOTLIN)

    api(project(":core:core-pie-chart"))
    implementation(project(":core:core-api"))

    implementation(Libraries.FLEXBOX)
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.APPCOMPAT)

    implementationAll(Libraries.Adapter.DEPENDENCIES)

    // Timber.
    implementation(Libraries.TIMBER)

}