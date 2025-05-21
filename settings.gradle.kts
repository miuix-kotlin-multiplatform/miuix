// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://raw.githubusercontent.com/miuix-kotlin-multiplatform/maven-repository/main/repository/snapshots")
    }
}

plugins {
    id("com.android.settings") version ("8.10.0")
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.4.0")
}

android {
    compileSdk = 36
    targetSdk = 36
    minSdk = 26
}

rootProject.name = "miuix"
include(":miuix", ":example")
