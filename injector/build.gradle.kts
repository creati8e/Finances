plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":core:impl"))
    implementation(project(":feature:dashboard-setup-api"))

    implementation(Libraries.KOTLIN)
    // region DI.
    kapt(Libraries.Dagger.COMPILER)
    implementation(Libraries.Dagger.LIBRARY)
    // endregion
}