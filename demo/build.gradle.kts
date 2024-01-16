plugins {
    id("java")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(rootProject)

    implementation("org.jetbrains:annotations:24.0.1")
}
