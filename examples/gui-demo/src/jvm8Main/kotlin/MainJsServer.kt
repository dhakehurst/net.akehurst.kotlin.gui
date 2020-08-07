package net.akehurst.kotlin.gui.examples.hello.world

import io.ktor.http.content.default
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            default("index.html")
            static("/") {
                resources("")
            }
        }
    }
    server.start(wait = true)

}