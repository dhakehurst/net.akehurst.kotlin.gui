plugins {
    id("org.openjfx.javafxplugin") version "0.0.9"
}

val version_tornado: String by project

dependencies {

    commonMainApi(project(":gui-core"))

    //jvm8MainImplementation("no.tornado:tornadofx:$version_tornado")
}

javafx {
    configuration = "jvm8Implementation"
    version = "14"
    modules = listOf( "javafx.controls", "javafx.fxml")
}