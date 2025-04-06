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
            description.set("A UI library for Compose MultiPlatform")
            url.set("https://github.com/miuix-kotlin-multiplatform/miuix")

            licenses {
                license {
                    name.set("Apache-2.0")
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
