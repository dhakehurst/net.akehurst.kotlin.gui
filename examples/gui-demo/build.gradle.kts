plugins {
//    kotlin("multiplatform") version ("1.4.0-rc")
    kotlin("multiplatform") version ("1.3.72")
}

val version_project: String by project
val group_project = "${rootProject.name}"

group = group_project
version = version_project

buildDir = File(rootProject.projectDir, ".gradle-build/${project.name}")

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()

    maven {
        name = "soywiz"
        setUrl("https://dl.bintray.com/korlibs/korlibs/")
    }

}

kotlin {
    jvm("jvm8") {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
        val test by compilations.getting {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    js("js") {
        nodejs()
        browser()
    }

    sourceSets {
        val jvm8Main by getting {
            resources.srcDir("$buildDir/distributions") // to make the build JS code available as a resource
        }
    }
}

dependencies {

    "commonMainImplementation"("net.akehurst.kotlin.gui:gui-core:0.0.1")
    "commonMainImplementation"("net.akehurst.kotlin.gui:gui-korui:0.0.1")

    "jvm8MainImplementation"("net.akehurst.kotlin.gui:gui-jfx:0.0.1")

    "jvm8MainImplementation"("io.ktor:ktor-server-netty:1.3.1")


    "commonMainImplementation"(kotlin("stdlib"))
    "commonTestImplementation"(kotlin("test"))
    "commonTestImplementation"(kotlin("test-annotations-common"))

    "jvm8MainImplementation"(kotlin("stdlib-jdk8"))
    "jvm8TestImplementation"(kotlin("test-junit"))

    "jsMainImplementation"(kotlin("stdlib-js"))
    "jsTestImplementation"(kotlin("test-js"))
}