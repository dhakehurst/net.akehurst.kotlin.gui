package net.akehurst.kotlin.gui.examples.hello.world

import net.akehurst.kotlin.gui.api.GuiFactory
import net.akehurst.kotlin.gui.core.guiWindow

class Gui(val factory: GuiFactory) {

    val win = guiWindow(factory,"Hello World Application") {
        text("Hello World!")
        val dialog1 = dialog("Progress") {
            progressBar()
        }
        button() {
            onPress {
                dialog1.show()
            }
        }
    }

    fun start() {
        win.show()
    }
}