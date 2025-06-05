// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.spotless)
}

val appName = "Miuix"
val pkgName = "top.yukonga.miuix.uitest"
val verName = "1.0.4"
val verCode = getVersionCode()
val generatedSrcDir = layout.buildDirectory.dir("generated").get().asFile.resolve("miuix-example")

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

kotlin {
    jvmToolchain(21)

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.compilerOptions {
            freeCompilerArgs.add("-Xbinary=preCodegenInlineThreshold=40")
        }
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    listOf(
        macosX64(),
        macosArm64(),
    ).forEach {
        it.compilerOptions {
            freeCompilerArgs.add("-Xbinary=preCodegenInlineThreshold=40")
        }
        it.binaries.executable {
            entryPoint = "main"
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            outputModuleName = "uitest"
            commonWebpackConfig {
                outputFileName = "uitest.js"
            }
        }
        binaries.executable()
    }

    js(IR) {
        browser {
            outputModuleName = "uitest"
            commonWebpackConfig {
                outputFileName = "uitest.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting
        val commonMain by getting {
            kotlin.srcDir(generatedSrcDir.resolve("kotlin").absolutePath)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(libs.haze)

            implementation(project(":miuix"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

dependencies {
    implementation(compose.preview)
    debugImplementation(compose.uiTooling)
}

android {
    namespace = pkgName
    defaultConfig {
        applicationId = pkgName
        versionCode = verCode
        versionName = verName
    }
    val properties = Properties()
    runCatching { properties.load(project.rootProject.file("local.properties").inputStream()) }
    val keystorePath = properties.getProperty("KEYSTORE_PATH") ?: System.getenv("KEYSTORE_PATH")
    val keystorePwd = properties.getProperty("KEYSTORE_PASS") ?: System.getenv("KEYSTORE_PASS")
    val alias = properties.getProperty("KEY_ALIAS") ?: System.getenv("KEY_ALIAS")
    val pwd = properties.getProperty("KEY_PASSWORD") ?: System.getenv("KEY_PASSWORD")
    if (keystorePath != null) {
        signingConfigs {
            register("github") {
                storeFile = file(keystorePath)
                storePassword = keystorePwd
                keyAlias = alias
                keyPassword = pwd
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    } else {
        signingConfigs {
            register("release") {
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }
    dependenciesInfo.includeInApk = false
    packaging {
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName = "$appName-v$versionName($versionCode)-$name.apk"
            }
        }
        resources.excludes += "**"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            vcsInfo.include = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules-android.pro")
            signingConfig = signingConfigs.getByName(if (keystorePath != null) "github" else "release")
        }
        debug {
            if (keystorePath != null) signingConfig = signingConfigs.getByName("github")
        }
    }
}

compose.desktop {
    application {
        mainClass = "Main_desktopKt"
        buildTypes.release.proguard {
            configurationFiles.from("proguard-rules-jvm.pro")
        }
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = appName
            packageVersion = verName

            macOS.iconFile = project.file("src/desktopMain/resources/macos/icon.icns")
            linux.iconFile = project.file("src/desktopMain/resources/linux/icon.png")
            windows.iconFile = project.file("src/desktopMain/resources/windows/icon.ico")
        }
    }
    nativeApplication {
        targets(kotlin.targets.getByName("macosArm64"), kotlin.targets.getByName("macosX64"))
        distributions {
            targetFormats(TargetFormat.Dmg)
            packageName = appName
            packageVersion = verName

            macOS.iconFile = project.file("src/macosMain/resources/Miuix.icns")
        }
    }
}

rootProject.plugins.withType<YarnPlugin> {
    rootProject.the<YarnRootExtension>().lockFileDirectory = rootProject.file("example").resolve("kotlin-js-store")
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

fun getGitCommitCount(): Int {
    val process = Runtime.getRuntime().exec(arrayOf("git", "rev-list", "--count", "HEAD"))
    return process.inputStream.bufferedReader().use { it.readText().trim().toInt() }
}

fun getVersionCode(): Int = getGitCommitCount()

val generateVersionInfo by tasks.registering {
    doLast {
        val file = generatedSrcDir.resolve("kotlin/misc/VersionInfo.kt")
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
        file.writeText(
            """
            package misc

            object VersionInfo {
                const val VERSION_NAME = "$verName"
                const val VERSION_CODE = $verCode
                const val JDK_VERSION = "${System.getProperty("java.version")}"

            }
            """.trimIndent(),
        )
    }
}

tasks.named("generateComposeResClass").configure {
    dependsOn(generateVersionInfo)
}
