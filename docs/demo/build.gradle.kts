import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
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
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta01")
            implementation(project(":miuix"))
        }
    }
}

rootProject.plugins.withType<YarnPlugin> {
    rootProject.the<YarnRootExtension>().lockFileDirectory = rootProject.file("docs/demo").resolve("kotlin-js-store")
}
