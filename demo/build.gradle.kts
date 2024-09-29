plugins {
    id("application")
}

dependencies {
    implementation(rootProject)
    implementation(libs.jetbrains.annotations)
}

tasks.withType<Javadoc> {
    exclude("**")
}

application {
    mainClass.set("org.teleight.teleightbots.demo.MainDemo")
}
