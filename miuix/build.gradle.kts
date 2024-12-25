import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    id("module.publication")
    id("org.jetbrains.dokka")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

kotlin {
    withSourcesJar(true)

    jvmToolchain(21)

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
            implementation(libs.androidx.graphics.shapes)
            implementation(libs.androidx.window)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.compose.window.size)
        }
        nativeMain.dependencies {
            implementation(libs.androidx.graphics.shapes)
        }
        desktopMain.dependencies {
            implementation(libs.androidx.graphics.shapes)
        }
        jsMain.dependencies {
            implementation(libs.androidx.graphics.shapes.web)
        }
        wasmJsMain.dependencies {
            implementation(libs.androidx.graphics.shapes.web)
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