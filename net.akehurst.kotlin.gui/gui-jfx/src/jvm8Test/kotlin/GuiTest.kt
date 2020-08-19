package net.akehurst.kotlin.gui.jfx

import net.akehurst.kotlin.gui.core.guiWindow
import kotlin.test.Test

class GuiTest {

    @Test
    fun t() {
        val factory = GuiFactoryJfx()
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
        win.show()
        Thread.sleep(1000000)
    }

}