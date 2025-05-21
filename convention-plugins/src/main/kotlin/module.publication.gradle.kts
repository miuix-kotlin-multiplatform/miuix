// Copyright 2025, miuix-kotlin-multiplatform contributors
// SPDX-License-Identifier: Apache-2.0

import java.io.FileInputStream
import java.util.Properties

plugins {
    `maven-publish`
    signing
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val githubUrl = "https://github.com"
val projectUrl = "$githubUrl/miuix-kotlin-multiplatform/miuix"

publishing {
    // Configure the publication repository
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
    // Configure all publications
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(javadocJar.get())
        // Provide artifacts information required
        pom {
            name.set("miuix")
            description.set("A UI library for Compose MultiPlatform")
            url.set(projectUrl)
            licenses {
                license {
                    name.set("The Apache Software License, Version 2.0")
                    url.set("$projectUrl/blob/main/LICENSE")
                }
            }
            issueManagement {
                system.set("Github")
                url.set("$projectUrl/issues")
            }
            scm {
                connection.set("$projectUrl.git")
                url.set(projectUrl)
            }
            developers {
                developer {
                    id.set("YuKongA")
                    name.set("YuKongA")
                    url.set("$githubUrl/YuKongA")
                }
                developer {
                    id.set("HowieHChen")
                    name.set("Howie")
                    url.set("$githubUrl/HowieHChen")
                }
                developer {
                    id.set("Voemp")
                    name.set("Voemp")
                    url.set("$githubUrl/Voemp")
                }
            }
        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used
signing {
    val localPropertiesFile = project.rootProject.file("local.properties")
    val localProperties = Properties()
    if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use { fis ->
            localProperties.load(fis)
        }
    }
    val signingKey = localProperties.getProperty("GPG_SIGNING_KEY") ?: System.getenv("GPG_SIGNING_KEY")
    val signingPassword = localProperties.getProperty("GPG_SIGNING_KEY") ?: System.getenv("GPG_PASSPHRASE")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

// Ensure all publish tasks depend on corresponding sign tasks
tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}