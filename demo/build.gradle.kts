plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(rootProject)

    implementation("org.jetbrains:annotations:24.0.1")
}

tasks.withType<Javadoc> {
    exclude("**")
}

application {
    mainClass.set("org.teleight.teleightbots.demo.MainDemo")
}
