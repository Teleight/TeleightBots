rootProject.name = "TeleightBots"
include("demo")

val isCiServer = System.getenv().containsKey("CI")
buildCache {
    local {
        isEnabled = !isCiServer
    }
}
