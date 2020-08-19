package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View
import net.akehurst.kotlin.gui.api.GuiText
import net.akehurst.kotlin.gui.korui.views.Text3D

class GuiTextKorui : GuiText, GuiControlKoruiAbstract() {

    override val korui3D = Text3D()

    override var text: String
        get() = korui3D.text
        set(value) { korui3D.text = value }


}