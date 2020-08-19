package net.akehurst.kotlin.gui.jfx

import javafx.scene.layout.VBox
import javafx.scene.text.Text
import net.akehurst.kotlin.gui.api.GuiText

class GuiTextJfx : GuiText, GuiControlJfxAbstract() {

    override val jfx = VBox()
    val textControl = Text()

    init {
        jfx.children.add(textControl)
    }

    override var text: String
        get() = textControl.text
        set(value) { textControl.text = value }


}