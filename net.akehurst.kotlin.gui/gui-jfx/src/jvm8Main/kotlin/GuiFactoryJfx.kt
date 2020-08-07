package net.akehurst.kotlin.gui.jfx

import javafx.application.Platform
import net.akehurst.kotlin.gui.api.*

class GuiFactoryJfx : GuiFactory {

    init {
        Platform.startup({
            // This block will be executed on JavaFX Thread
        })
    }

    override fun createWindow(): GuiWindow {
        return GuiWindowJfx()
    }


    override fun createButton(container: GuiContainer): GuiButton {
        TODO("not implemented")
    }

    override fun createDialog(container: GuiContainer): GuiDialog {
        TODO("not implemented")
    }

    override fun createProgressBar(container: GuiContainer): GuiProgressBar {
        TODO("not implemented")
    }

    override fun createText(container: GuiContainer): GuiText {
        val el = GuiTextJfx()
        container.addContent(el)
        return el
    }

    override fun createTextEditor(container: GuiContainer): GuiTextEditor {
        TODO("not implemented")
    }

}