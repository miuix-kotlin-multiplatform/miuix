import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    id("module.publication")
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
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.window)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.androidx.graphics.shapes)
            implementation(libs.compose.window.size)
        }
    }
}

android {
    namespace = "top.yukonga.miuix.kmp"
}