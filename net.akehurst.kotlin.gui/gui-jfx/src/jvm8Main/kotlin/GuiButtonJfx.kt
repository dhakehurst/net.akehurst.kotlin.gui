package net.akehurst.kotlin.gui.jfx

import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Button
import net.akehurst.kotlin.gui.api.GuiButton

class GuiButtonJfx : GuiButton, GuiControlJfxAbstract() {

    // --- GuiControlJfx ---
    override val jfx = Button()

    // --- GuiButton ---
    override var label: String
        get() = jfx.text
        set(value) {jfx.text = value}


    override var onPressed: () -> Unit
        get() = TODO("not implemented")
        set(value) {}

    private var _onReleased = {}
    override var onReleased: () -> Unit
        get() = _onReleased
        set(value) {
            _onReleased = value
            jfx.onAction = EventHandler { e -> _onReleased() } //TODO: pass event details
        }
}