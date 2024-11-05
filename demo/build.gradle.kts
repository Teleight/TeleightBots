plugins {
    id("application")
}

dependencies {
    implementation(rootProject)
    implementation(libs.jetbrains.annotations)

    implementation("ch.qos.logback:logback-classic:1.5.12")
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
