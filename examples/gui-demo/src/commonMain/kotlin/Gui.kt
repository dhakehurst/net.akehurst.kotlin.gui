package net.akehurst.kotlin.gui.examples.hello.world

import net.akehurst.kotlin.gui.api.GuiFactory
import net.akehurst.kotlin.gui.core.guiWindow

class Gui(val factory: GuiFactory) {

    val win = guiWindow(factory, "Demo Application") {
        panel {
            text("Hello World!")
            val dialog1 = dialog("Progress") {
                val d1 = this.control
                panel {
                    progressBar()
                    button("Cancel") {
                        onReleased {
                            d1.close()
                        }
                    }
                }
            }
            button("Press Me") {
                onReleased {
                    dialog1.show()
                }
            }
        }
    }

    fun start() {
        win.show()
    }
}