@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.multiplatform)
}

val appName = "Miuix"
val pkgName = "top.yukonga.miuix.uitest"
val verName = "1.0.3"
val verCode = getVersionCode()
val xcf = XCFramework(appName + "Framework")

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

kotlin {
    jvmToolchain(21)

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = appName + "Framework"
            isStatic = true
            freeCompilerArgs += "-Xbinary=bundleId=$pkgName.framework"
            xcf.add(this)
        }
    }

    listOf(
        macosX64(),
        macosArm64()
    ).forEach { macosTarget ->
        macosTarget.binaries.executable {
            baseName = appName
            entryPoint = "main"
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal"
            )
        }
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "uitest"
        browser {
            commonWebpackConfig {
                outputFileName = "uitest.js"
            }
        }
        binaries.executable()
    }

    js(IR) {
        moduleName = "uitest"
        browser {
            commonWebpackConfig {
                outputFileName = "uitest.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)

            implementation(compose.material3)
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
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = pkgName
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName(if (keystorePath != null) "github" else "release")
        }
        debug {
            if (keystorePath != null) signingConfig = signingConfigs.getByName("github")
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        buildTypes.release.proguard {
            configurationFiles.from("proguard-rules-jvm.pro")
            version.set("7.6.0")
            optimize.set(false)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = appName
            packageVersion = verName
        }
    }
}

fun getGitCommitCount(): Int {
    val process = Runtime.getRuntime().exec(arrayOf("git", "rev-list", "--count", "HEAD"))
    return process.inputStream.bufferedReader().use { it.readText().trim().toInt() }
}

fun getVersionCode(): Int {
    return getGitCommitCount()
}

val generateVersionInfo by tasks.registering {
    doLast {
        val jdkVersion = System.getProperty("java.version")
        val file = file("src/commonMain/kotlin/utils/VersionInfo.kt")
        file.writeText(
            """
            package utils
            
            object VersionInfo {
                const val VERSION_NAME = "$verName"
                const val VERSION_CODE = $verCode
                const val JDK_VERSION = "$jdkVersion"
            }
            """.trimIndent()
        )
    }
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateVersionInfo)
}

tasks.register<Exec>("assembleMiuixMacosArm64ReleaseBinary") {
    dependsOn(":composeApp:desktopTest", ":composeApp:linkReleaseExecutableMacosArm64")
    commandLine("lipo", "-create", "-output", "Miuix_macOSArm64", "bin/macosArm64/releaseExecutable/Miuix.kexe")
    workingDir = layout.buildDirectory.get().asFile
    group = "macos native"
    description = "Build macOS Arm64 binary"
}

tasks.register<Exec>("assembleMiuixMacosX64ReleaseBinary") {
    dependsOn(":composeApp:desktopTest", ":composeApp:linkReleaseExecutableMacosX64")
    commandLine("lipo", "-create", "-output", "Miuix_macOSX64", "bin/macosX64/releaseExecutable/Miuix.kexe")
    workingDir = layout.buildDirectory.get().asFile
    group = "macos native"
    description = "Build macOS X64 binary"
}

tasks.register<Exec>("assembleMiuixMacosUniversalReleaseBinary") {
    dependsOn(":composeApp:desktopTest", ":composeApp:linkReleaseExecutableMacosX64", ":composeApp:linkReleaseExecutableMacosArm64")
    commandLine("lipo", "-create", "-output", "Miuix_macOS", "bin/macosX64/releaseExecutable/Miuix.kexe", "bin/macosArm64/releaseExecutable/Miuix.kexe")
    workingDir = layout.buildDirectory.get().asFile
    group = "macos native"
    description = "Build macOS universal binary"
}
