plugins {
    id("application")
}

dependencies {
    implementation(rootProject)
    implementation(libs.jetbrains.annotations)
    implementation(libs.logback)
    configurations.all {
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }

}

tasks.withType<Javadoc> {
    exclude("**")
}

application {
    mainClass.set("org.teleight.teleightbots.demo.MainDemo")
}
