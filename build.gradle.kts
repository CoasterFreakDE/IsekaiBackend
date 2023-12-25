import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("plugin.serialization") version "1.9.10"
}

group = "de.coasterfreak"
version = "1.0-SNAPSHOT"

val ktorVersion: String by project
val exposedVersion: String by project

repositories {
    mavenCentral()
    maven("https://repo.flawcra.cc/mirrors")
}

val shadowDependencies = listOf(
    "io.javalin:javalin-bundle:5.6.3",
    "com.google.code.gson:gson:2.10.1",

    "org.litote.kmongo:kmongo:4.10.0",
    "org.litote.kmongo:kmongo-coroutine:4.10.0",

    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0",

    "com.github.TheFruxz:Ascend:2023.3",
    "io.github.cdimascio:dotenv-kotlin:6.4.1",
)

dependencies {
    testImplementation(kotlin("test"))

    shadowDependencies.forEach { dependency ->
        implementation(dependency)
        shadow(dependency)
    }
}


tasks {

    test {
        useJUnitPlatform()
    }

    build {
        dependsOn("shadowJar")
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    withType<ShadowJar> {
        mergeServiceFiles()
        configurations = listOf(project.configurations.shadow.get())
        archiveFileName.set("IsekaiBackend.jar")

        manifest {
            attributes["Main-Class"] = "de.coasterfreak.isekaibackend.StartKt"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

kotlin {
    jvmToolchain(17)
}