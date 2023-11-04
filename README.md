<h1>Teleight Bots 
<sub><sub><sub><sub><sub>The most lightweight java telegram bot wrapper</sub></sub></sub></sub></sub>
</h1>

Teleight bots is a java telegram bot wrapper written with performance and ease of use at mind. It fully utilizes the modern java APIs in order to have a more flexible environment for developers

**The project is still in an alpha phase, and most bot api methods are not yet available. Please see [how to contribute](CONTRIBUTING.md)**

## Features
Teleight is written towards the latest Java 21 LTS, thus providing the latest features provided by this version.

- It provides a multi threaded design to deal with most computational tasks.
- It mostly uses native calls to reduce overhead to the minimum

## Installation

Clone this repo, and run this command
```bash
./gradlew publishToMavenLocal
```

Then, in your project, add the dependency
```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("org.teleight:TeleightBots:1.0")
}
```
# Usage

An example of how to use the TeleightBots library is available [here](/demo).

## Contributing
We welcome all submissions to this project, but please follow the contributing guidelines situated in [CONTRIBUTING.md](CONTRIBUTING.md)

# Credits
* The [contributors](https://github.com/Teleight/TeleightBots/graphs/contributors) of the project.
* [Minestom](https://github.com/Minestom/Minestom) for the amazing api design.

# License
This project is licensed under the [GNU General Public License v3.0](LICENSE).
