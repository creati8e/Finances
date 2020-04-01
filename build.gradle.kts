import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BuildType
import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.github.ben-manes.versions") version ("0.28.0")
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(BuildScript.Plugins.GMS)
        classpath(BuildScript.Plugins.KOTLIN)
        classpath(BuildScript.Plugins.JUNIT5)
        classpath(BuildScript.Plugins.ANDROID)
        classpath(BuildScript.Plugins.APP_BADGE)
        classpath(BuildScript.Plugins.PROGUARD_GENERATOR)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("http://jitpack.io/")
    }
}

subprojects {
    enableInlineClasses()
    forceDependencyVersions()
    afterEvaluate {
        extensions
            .findByType(TestedExtension::class.java)
            ?.apply {
                with(this) {

                    defaultConfig {
                        versionCode = AppConfig.VERSION_CODE
                        versionName = AppConfig.VERSION_NAME
                        minSdkVersion(AppConfig.MIN_SDK)
                        targetSdkVersion(AppConfig.TARGET_SDK)
                    }
                    configureBuildTypes()

                    compileSdkVersion(AppConfig.TARGET_SDK)
                    buildToolsVersion("29.0.3")

                    sourceSets.forEach { sourceSet ->
                        sourceSet.java.srcDir("src/${sourceSet.name}/kotlin")
                    }

                    with(compileOptions) {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                    packagingOptions {
                        pickFirst("META-INF/atomicfu.kotlin_module")
                        exclude("META-INF/*.kotlin_module")
                        exclude("**.kotlin_builtins")
                        exclude("**.kotlin_metadata")
                    }
                }
            }
    }
}

// Set build types for android module.
fun TestedExtension.configureBuildTypes() {

    fun BuildType.configProguard(isLibrary: Boolean): BuildType {
        return if (isLibrary) {
            consumerProguardFile(File("proguard-rules.pro"))
        } else {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                File("proguard-rules.pro")
            )
        }
    }

    with(this) {

        val isLibrary = this@configureBuildTypes is LibraryExtension

        buildTypes {
            maybeCreate(AppConfig.BuildTypes.RELEASE.name).apply {
                isMinifyEnabled = true
                isDebuggable = false
                configProguard(isLibrary)
            }
            maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
                isMinifyEnabled = true
                isDebuggable = false
                configProguard(isLibrary)
            }
            maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
                isDebuggable = true
            }
        }
    }
}


fun Project.enableInlineClasses() {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            kotlinOptions.freeCompilerArgs = kotlinOptions.freeCompilerArgs + listOf(
                "-XXLanguage:+InlineClasses",
                "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
            )
        }
    }
}

fun Project.forceDependencyVersions() {
    configurations
        .all {
            resolutionStrategy {
                force(Libraries.KOTLIN)
            }
        }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}