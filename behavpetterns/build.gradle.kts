plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    //implementation("io.reactivex.rxjava3:rxkotlin:3.1.9")
    implementation("io.reactivex.rxjava3:rxjava:3.1.9")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Replace with the latest version
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}