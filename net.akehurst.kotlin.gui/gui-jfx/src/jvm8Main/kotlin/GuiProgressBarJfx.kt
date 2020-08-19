package net.akehurst.kotlin.gui.jfx

import javafx.scene.Node
import javafx.scene.control.ProgressBar
import net.akehurst.kotlin.gui.api.GuiProgressBar

class GuiProgressBarJfx : GuiProgressBar, GuiControlJfxAbstract() {

    override val jfx = ProgressBar()

    override var progress: Double
        get() = jfx.progress
        set(value) {jfx.progress = value}

    override var width: Double
        get() = jfx.width
        set(value) {jfx.prefWidth = value}
    override var height: Double
        get() = jfx.height
        set(value) {jfx.prefHeight = value}
}