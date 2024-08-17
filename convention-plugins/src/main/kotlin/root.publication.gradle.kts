plugins {
    id("io.github.gradle-nexus.publish-plugin")
}

allprojects {
    group = "top.yukonga.miuix.kmp"
    version = "0.0.1"
}

//nexusPublishing {
//    // Configure maven central repository
//    // https://github.com/gradle-nexus/publish-plugin#publishing-to-maven-central-via-sonatype-ossrh
//    repositories {
//        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
//            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
//            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
//        }
//    }
//}
