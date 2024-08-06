import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
}

publishing {
    publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
        })

        // Provide artifacts information required by Maven Central
        pom {
            name.set("Miuix-like UI")
            description.set("Miuix-like UI library for Kotlin Multiplatform")
            url.set("https://github.com/YuKongA/miuix-kotlin-multiplatform")

            licenses {
                license {
                    name.set("Apache-2.0 license")
                    url.set("https://github.com/YuKongA/miuix-kotlin-multiplatform/blob/main/LICENSE")
                }
            }
            developers {
                developer {
                    id.set("YuKongA")
                    name.set("YuKongA")
                    url.set("https://github.com/YuKongA")
                }
            }
            scm {
                url.set("https://github.com/YuKongA/miuix-kotlin-multiplatform")
            }
        }
    }
}

signing {
    if (project.hasProperty("signing.gnupg.keyName")) {
        useGpgCmd()
        sign(publishing.publications)
    }
}
