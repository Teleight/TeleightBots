import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-library`
    alias(libs.plugins.shadow)
    alias(libs.plugins.publisher)
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
    api(libs.slf4j.api)

    implementation(libs.slf4j.simple)

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

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    coordinates(groupId, artifactId, versionId)

    signAllPublications()

    pom {
        name.set("TeleightBots")
        description.set(project.description)
        url.set("https://github.com/Teleight/TeleightBots")
        inceptionYear.set("2024")

        licenses {
            license {
                name.set("The GNU General Public License v3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.en.html")
            }
        }

        developers {
            developer {
                id.set("Stredox02")
                url.set("https://github.com/Stredox02")
            }
            developer {
                id.set("literallyfiro")
                url.set("https://github.com/literallyfiro")
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
