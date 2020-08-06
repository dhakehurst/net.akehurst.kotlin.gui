plugins {
    kotlin("multiplatform") version ("1.4.0-rc")
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
}

dependencies {

    "commonMainImplementation"("net.akehurst.kotlin.gui:gui-core:0.0.1")

    "jvm8MainImplementation"("net.akehurst.kotlin.gui:gui-jfx:0.0.1")
}