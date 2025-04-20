plugins {
    `maven-publish`
    signing
}

val githubUrl = "https://github.com"
val projectUrl = "$githubUrl/miuix-kotlin-multiplatform/miuix"

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
