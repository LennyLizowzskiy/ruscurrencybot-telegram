plugins {
    kotlin("js") version "1.6.21"
}

group = "ru.lizowzskiy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // NPM dependencies
    implementation(npm("moment", "2.29.3"))
    implementation(npm("needle", "3.1.0"))
    implementation(npm("node-telegram-bot-api", "0.57.0"))
    implementation(npm("xml-js", "1.6.11"))
    implementation(npm("puppeteer", "13.7.0"))

    // Test
    testImplementation(kotlin("test"))
}

kotlin {
    js(IR) {
        moduleName = "RusCurrencyConverterTGBot"
        nodejs {

        }
        binaries.executable()
    }
}