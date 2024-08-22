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
            name.set("miuix")
            description.set("A MiuiX-like UI for Kotlin Multiplatform")
            url.set("https://github.com/miuix-kotlin-multiplatform/miuix")

            licenses {
                license {
                    name.set("Apache-2.0 license")
                    url.set("https://github.com/miuix-kotlin-multiplatform/miuix/blob/main/LICENSE")
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
                url.set("https://github.com/miuix-kotlin-multiplatform/miuix")
            }
        }
    }

    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}
