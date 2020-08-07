package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.html.Html
import com.soywiz.korge.view.Fonts
import com.soywiz.korim.color.Colors
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
        el.korui.format = Html.Format(color = Colors["#000000"], face = Html.FontFace.Bitmap(Fonts.defaultFont), size = 16)
        container.addContent(el)
        return el
    }

    override fun createTextEditor(container: GuiContainer): GuiTextEditor {
        TODO("not implemented")
    }

    override fun createProgressBar(container: GuiContainer): GuiProgressBar {
        val el = GuiProgressBarKorui()
        container.addContent(el)
        return el
    }
}