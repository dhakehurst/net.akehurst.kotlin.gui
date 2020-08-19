package net.akehurst.kotlin.gui.jfx

import javafx.scene.Node
import javafx.scene.control.Control
import javafx.scene.layout.Region
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiLayout

abstract class GuiControlJfxAbstract : GuiControl {

    abstract val jfx : Region

    override var width: Double
        get() = jfx.prefWidth
        set(value) {jfx.prefWidth = value}
    override var height: Double
        get() = jfx.prefHeight
        set(value) {jfx.prefHeight = value}

    override fun applyLayout(guiLayout: GuiLayout) {
        TODO("not implemented")
    }
}