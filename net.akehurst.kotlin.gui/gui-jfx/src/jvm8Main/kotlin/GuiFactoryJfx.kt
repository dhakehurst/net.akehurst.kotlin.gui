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
        val el = GuiButtonJfx()
        container.addContent(el)
        return el
    }

    override fun createDialog(container: GuiContainer): GuiDialog {
        val el = GuiDialogJfx()
        //container.addContent(el)
        return el
    }

    override fun createPanel(container: GuiContainer): GuiPanel {
        val el = GuiPanelJfx()
        container.addContent(el)
        return el
    }

    override fun createProgressBar(container: GuiContainer): GuiProgressBar {
        val el = GuiProgressBarJfx()
        container.addContent(el)
        return el
    }

    override fun createText(container: GuiContainer): GuiText {
        val el = GuiTextJfx()
        container.addContent(el)
        return el
    }

    override fun createTextEditor(container: GuiContainer): GuiTextEditor {
        TODO("not implemented")
    }

    override fun createCube(container: GuiContainer): GuiCube {
        TODO("not implemented")
    }

}