package net.akehurst.kotlin.gui.examples.hello.world

import net.akehurst.kotlin.gui.api.*

class Application(
        val factory:GuiFactory
) {

    val gui = Gui(factory)

    fun start() {
        gui.start()
    }

}

