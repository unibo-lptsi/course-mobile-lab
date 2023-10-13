plugins {
    kotlin("jvm") version "1.9.10"
    // https://docs.gradle.org/current/samples/sample_building_kotlin_applications.html
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

val mainClass: String by project 

application {
    mainClass.set(project.properties["mainClass"].toString())
}