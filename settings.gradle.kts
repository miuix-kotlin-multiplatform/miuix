@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("convention-plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "miuix-kotlin-multiplatform"
include(":miuix", ":composeApp")
