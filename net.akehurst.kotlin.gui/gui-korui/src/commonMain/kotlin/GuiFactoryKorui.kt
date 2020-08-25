package net.akehurst.kotlin.gui.korui

import com.soywiz.korim.color.Colors
import com.soywiz.korge.html.Html
import com.soywiz.korge.view.Fonts

import net.akehurst.kotlin.gui.api.*

class GuiFactoryKorui : GuiFactory {

    override fun createWindow(): GuiWindow {
        return GuiWindowKorui()
    }

    override fun createButton(container: GuiContainer): GuiButton {
        val el = GuiButtonKorui()
        container.addContent(el)
        return el
    }

    override fun createDialog(container: GuiContainer): GuiDialog {
        val el = GuiDialogKorui()
        container.addContent(el)
        return el
    }

    override fun createText(container: GuiContainer): GuiText {
        val el = GuiTextKorui()
        el.korui3D.format = Html.Format(color = Colors.WHITE, face = Html.FontFace.Font(Fonts.defaultFont), size = 16)
        container.addContent(el)
        return el
    }

    override fun createTextEditor(container: GuiContainer): GuiTextEditor {
        TODO("not implemented")
    }

    override fun createPanel(container: GuiContainer): GuiPanel {
        val el = GuiPanelKorui()
        container.addContent(el)
        return el
    }
    override fun createProgressBar(container: GuiContainer): GuiProgressBar {
        val el = GuiProgressBarKorui()
        container.addContent(el)
        return el
    }
}