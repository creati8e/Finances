plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":core:core-impl"))
    implementation(project(":feature:feature-dashboard-setup-api"))
    implementation(project(":feature:feature-dashboard-setup-impl"))

    implementation(Libraries.KOTLIN)

    implementation(Libraries.Dagger.LIBRARY)
    kapt(Libraries.Dagger.COMPILER)
}