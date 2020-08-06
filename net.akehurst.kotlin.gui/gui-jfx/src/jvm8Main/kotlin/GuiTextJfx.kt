package net.akehurst.kotlin.gui.jfx

import javafx.scene.text.Text
import net.akehurst.kotlin.gui.api.GuiText

class GuiTextJfx : GuiText, GuiControlJfx {

    override val jfx = Text()

    override var text: String
        get() = jfx.text
        set(value) { jfx.text = value }
}