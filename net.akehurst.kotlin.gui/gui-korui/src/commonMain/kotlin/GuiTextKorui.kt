package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View
import net.akehurst.kotlin.gui.api.GuiText

class GuiTextKorui : GuiText, GuiControlKorui {

    override val korui = Text()

    override var text: String
        get() = korui.text
        set(value) { korui.text = value }
}