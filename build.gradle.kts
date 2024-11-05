plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
}

val groupId = "org.teleight"
val artifactId = "TeleightBots"
val descriptionId = "Java library for Telegram Bots"
val versionId = "1.0"

allprojects {
    apply(plugin = "java")

    group = groupId
    version = versionId
    description = descriptionId

    repositories {
        mavenCentral()
        mavenLocal()
    }

    java {
        withSourcesJar()
        withJavadocJar()

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

dependencies {
    api(libs.bundles.jackson)
    api(libs.bundles.checker)
    api(libs.jetbrains.annotations)
    api(libs.bundles.javalin)
    api(libs.slf4j.api)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.bundles.junit)
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    options {
        require(this is StandardJavadocDocletOptions)
        addStringOption("Xdoclint:all,-missing", "-quiet")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupId
            artifactId = artifactId
            version = versionId

            from(project.components["java"])

            pom {
                name.set(this@create.artifactId)
                description.set(project.description)
                url.set("https://github.com/Teleight/TeleightBots")

                licenses {
                    license {
                        name.set("The GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    }
                }

                developers {
                    developer {
                        id.set("Stredox02")
                    }
                    developer {
                        id.set("literallyfiro")
                    }
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/Teleight/TeleightBots/issues")
                }

                scm {
                    connection.set("scm:git:git://github.com/Teleight/TeleightBots.git")
                    developerConnection.set("scm:git:git@github.com:Teleight/TeleightBots.git")
                    url.set("https://github.com/Teleight/TeleightBots")
                    tag.set("HEAD")
                }

                ciManagement {
                    system.set("Github Actions")
                    url.set("https://github.com/Teleight/TeleightBots/actions")
                }
            }
        }
    }
}
