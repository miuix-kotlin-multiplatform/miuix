import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    id("module.publication")
    id("org.jetbrains.dokka")
}

kotlin {
    withSourcesJar(true)

    jvmToolchain(17)

    androidTarget {
        publishLibraryVariants("release")
    }

    jvm("desktop")

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosArm64()
    macosX64()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    js(IR) {
        browser()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.window)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.jetbrains.compose.window.size)
        }
    }
}

android {
    namespace = "top.yukonga.miuix.kmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}