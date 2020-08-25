
repositories {
    maven {
        name= "soywiz"
        setUrl("https://dl.bintray.com/korlibs/korlibs/")
    }
}

val version_korlibs:String by project
val version_korge:String by project

dependencies {

    commonMainApi(project(":gui-core"))

    commonMainImplementation("com.soywiz.korlibs.korge:korge:$version_korge")
    commonMainImplementation("com.soywiz.korlibs.korim:korim:$version_korlibs")
}