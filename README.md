# 🤖 TeleightBots

![Java Version](https://img.shields.io/badge/Java_version-21-green)
![License](https://img.shields.io/github/license/Teleight/TeleightBots)
![API Version](https://img.shields.io/badge/Bot_API-9.0-blue?logo=telegram)

TeleightBots is a _lightweight, high-performance, easy to use_ Java Telegram Bot API wrapper.
It uses modern Java 21 APIs to provide a flexible and easier development experience.

> [!CAUTION]
>
> Teleight is still in early development! 
> If you find any problems, feel free to create an issue on GitHub.
> Contributions are welcome!

## 📚 Documentation
> [!IMPORTANT]
>
> If you're new to bot creation, start with the [Official Telegram Bots Documentation](https://core.telegram.org/bots) to
understand the basics.
>
An example of how to use the TeleightBots library is available [here](/demo).
Wiki and Javadocs are not yet available.

## 💻 Local Installation
To set up the library locally, follow these steps:

1. Clone the repository: `git clone https://github.com/Teleight/TeleightBots.git && cd TeleightBots`
2. Publish to maven local: `./gradlew publishToMavenLocal`
3. Add the dependency to your project

#### Gradle (kotlin)
```kotlin
repositories {
    mavenLocal() // Include the local Maven repository
}

dependencies {
    implementation("org.teleight:TeleightBots:VERSION") // Replace VERSION with the latest version
}
```
#### Gradle (groovy)
```groovy
repositories {
    mavenLocal() // Include the local Maven repository
}

dependencies {
    implementation 'org.teleight:TeleightBots:VERSION' // Replace VERSION with the latest version
}
```
#### Maven
```xml
<dependencies>
    <dependency>
        <groupId>org.teleight</groupId>
        <artifactId>TeleightBots</artifactId>
        <version>VERSION</version> <!-- Replace VERSION with the latest version -->
    </dependency>
</dependencies>
```

Make sure to replace `VERSION` with the latest version of the library.

## 📄 License
```
                    GNU GENERAL PUBLIC LICENSE
                       Version 3, 29 June 2007

 Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 Everyone is permitted to copy and distribute verbatim copies
 of this license document, but changing it is not allowed.
```
This project is licensed under the [GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html). All
contributions are accepted under the same license.

## 🤝 Contributing

Want to contribute?
Feel free to fork this repository and create a pull request with your changes.
Please ensure you follow
the [Contribution Guidelines](https://github.com/Teleight/TeleightBots/blob/master/CONTRIBUTING.md) before creating a
pull request.

## ⭐️ Star History
Give this repository a star to support the development!

[![Star History Chart](https://api.star-history.com/svg?repos=Teleight/TeleightBots&type=Date)](https://star-history.com/#Teleight/TeleightBots&Date)
