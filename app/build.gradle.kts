import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("ru.cleverpumpkin.badge")
    id("ru.cleverpumpkin.proguard-dictionaries-generator")
    id("androidx.navigation.safeargs")
}

android {
    defaultConfig {
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        applicationId = AppConfig.APPLICATION_ID
    }
    signingConfigs {
        getByName(AppConfig.BuildTypes.DEBUG.name) {
            keyPassword = "android"
            storePassword = "android"
            keyAlias = "androiddebugkey"
            storeFile = File(projectDir, "debug.keystore")
        }
    }
    buildTypes {
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.RELEASE.name)
    }
}

badge {
    iconNames = listOf("@mipmap/ic_launcher_foreground")
    buildTypes {
        create(AppConfig.BuildTypes.DEV.name) {
            enabled = true
            fontSize = 12
            text = AppConfig.VERSION_NAME
        }
        create(AppConfig.BuildTypes.DEBUG.name) {
            enabled = true
            fontSize = 12
            text = AppConfig.VERSION_NAME
        }
    }
}

proguardDictionaries {
    dictionaryNames = listOf(
        "build/class-dictionary",
        "build/method-dictionary"
    )
    minLineLength = 10
    maxLineLength = 20
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))

    // region Modules.

    implementation(project(":injector"))
    implementation(project(":core:core-impl"))
    implementation(project(":feature:feature-dashboard"))
    implementation(project(":feature:feature-onboarding"))
    implementation(project(":feature:feature-user-profile"))
    implementation(project(":feature:feature-transactions"))
    implementation(project(":feature:feature-authorization"))
    implementation(project(":feature:feature-money-accounts"))
    implementation(project(":feature:feature-categories-api"))
    implementation(project(":feature:feature-categories-impl"))
    implementation(project(":feature:feature-dashboard-setup-api"))
    implementation(project(":feature:feature-dashboard-setup-impl"))
    implementation(project(":feature:feature-money-account-details"))
    implementation(project(":feature:feature-money-account-creation"))

    // endregion

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementationAll(Libraries.Dagger.DEPENDENCIES)

    // endregion

    // region UI.

    implementation(Libraries.COIL)

    // Navigation.
    implementationAll(Libraries.Android.Navigation.DEPENDENCIES)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    // endregion

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

    // Firebase.
    implementation(Libraries.Infrastructure.FIRESTORE)
    implementation(Libraries.Infrastructure.AUTH)

}