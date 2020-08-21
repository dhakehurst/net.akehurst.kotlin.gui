
repositories {
    maven {
        name= "soywiz"
        setUrl("https://dl.bintray.com/korlibs/korlibs/")
    }
}

val version_korge:String by project

dependencies {

    commonMainApi(project(":gui-core"))

    commonMainImplementation("com.soywiz.korlibs.korge:korge:$version_korge")
}