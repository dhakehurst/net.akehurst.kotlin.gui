package net.akehurst.kotlin.gui.korui

import net.akehurst.kotlin.gui.api.GuiButton
import net.akehurst.kotlin.gui.korui.views.Button

class GuiButtonKorui : GuiButton, GuiControlKoruiAbstract() {

    override val korui = Button()


    override var label: String
        get() = korui.text.text
        set(value) {korui.text.text = value}

    override var onPressed: () -> Unit
        get() = korui.onPressed
        set(value) { korui.onPressed = value }

    override var onReleased: () -> Unit
        get() = korui.onReleased
        set(value) { korui.onReleased = value }


}