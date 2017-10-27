import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.headlessideas"
version = "0.1-SNAPSHOT"

val kotlin_version: String by extra

plugins {
    kotlin("jvm")
}

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.1.51"

    repositories {
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
    }
}


kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

repositories {
    jcenter()
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlin_version")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.16")
    compile("com.xenomachina:kotlin-argparser:2.0.3")
    compile("ch.qos.logback:logback-classic:1.0.13")
    compile("io.github.microutils:kotlin-logging:1.4")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val httpJar = task("httpJar", type = Jar::class) {
    baseName = project.name
    manifest {
        attributes["Main-Class"] = "com.headlessideas.http.example.AppKt"
    }
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(httpJar)
    }
}