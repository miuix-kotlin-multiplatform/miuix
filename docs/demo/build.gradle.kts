// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.spotless)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

kotlin {
    jvmToolchain(21)

    js(IR) {
        browser {
            outputModuleName = "demo"
            commonWebpackConfig {
                outputFileName = "demo.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
            implementation(project(":miuix"))
        }
    }
}

rootProject.plugins.withType<YarnPlugin> {
    rootProject.the<YarnRootExtension>().lockFileDirectory = rootProject.file("docs/demo").resolve("kotlin-js-store")
}

spotless {
    kotlin {
        target("src/**/*.kt")
        licenseHeaderFile(rootProject.file("./spotless/copyright.txt"), "(^(?![\\/ ]\\**).*$)")
    }

    kotlinGradle {
        target("*.kts")
        licenseHeaderFile(rootProject.file("./spotless/copyright.txt"), "(^(?![\\/ ]\\**).*$)")
    }
}