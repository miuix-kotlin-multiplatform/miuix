// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

plugins { kotlin("jvm") }

java { toolchain.languageVersion = JavaLanguageVersion.of(21) }

dependencies { implementation(project(":miuix")) }

val iconsSourceDir = rootProject.layout.projectDirectory.dir("miuix/src/commonMain/kotlin/top/yukonga/miuix/kmp/icon").asFile
val outputDir = layout.buildDirectory.dir("generated-svg")

tasks.register<JavaExec>("generateSvg") {
    group = "iconGen"
    description = "Generate SVGs from Compose ImageVector definitions"
    dependsOn(tasks.named("classes"))
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("top.yukonga.miuix.docs.icongen.MainKt")
    val lightColor = project.findProperty("iconLightColor")?.toString() ?: "#000000"
    val darkColor = project.findProperty("iconDarkColor")?.toString() ?: "#FFFFFF"
    val preserve = project.findProperty("iconPreserveColors")?.toString()?.equals("true", true) == true
    outputs.dir(outputDir)
    doFirst { outputDir.get().asFile.mkdirs() }
    args = listOf(
        "--src", iconsSourceDir.absolutePath,
        "--out", outputDir.get().asFile.absolutePath,
        "--light", lightColor,
        "--dark", darkColor,
        "--preserve-colors", preserve.toString()
    )
}
