import org.apache.commons.io.output.ByteArrayOutputStream
import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
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
            versionNameSuffix = "+${getLastCommitHash()}"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
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

    implementation(project(":core:impl"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:transaction"))
    implementation(project(":feature:user-profile"))
    implementation(project(":feature:transactions-report"))
    implementation(project(":feature:authorization"))
    implementation(project(":feature:money-accounts-list"))
    implementation(project(":feature:categories-list"))
    implementation(project(":feature:dashboard-setup-api"))
    implementation(project(":feature:dashboard-setup-impl"))
    implementation(project(":feature:money-account-details"))
    implementation(project(":feature:money-account"))
    implementation(project(":core:currency-choice-api"))
    implementation(project(":core:currency-choice-impl"))

    // endregion

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementation(Libraries.Dagger.LIBRARY)

    // endregion

    // region UI.

    implementation(Libraries.COIL)

    // Navigation.
    implementation(Libraries.Android.Navigation)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    // Firebase.
    implementation(Libraries.Infrastructure.AUTH)
    implementation(Libraries.Infrastructure.FIRESTORE)

}

fun getLastCommitHash(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
        standardOutput = stdout
    }
    @Suppress("DEPRECATION")
    return stdout.toString().trim()
}