package net.akehurst.kotlin.gui.jfx

import javafx.application.Platform
import net.akehurst.kotlin.gui.api.GuiContainer
import net.akehurst.kotlin.gui.api.GuiFactory
import net.akehurst.kotlin.gui.api.GuiText
import net.akehurst.kotlin.gui.api.GuiWindow

class GuiFactoryJfx : GuiFactory {

    init {
        Platform.startup(  {
            // This block will be executed on JavaFX Thread
        })
    }

    override fun createWindow(): GuiWindow {
        return GuiWindowJfx()
    }

    override fun createText(container: GuiContainer): GuiText {
        val el = GuiTextJfx()
        container.content.add(el)
        return el
    }
}