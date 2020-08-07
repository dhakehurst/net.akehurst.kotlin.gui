package net.akehurst.kotlin.gui.korui

import net.akehurst.kotlin.gui.api.GuiButton
import net.akehurst.kotlin.gui.korui.views.Button

class GuiButtonKorui : GuiButton, GuiControlKorui {

    override val korui = Button()


    override var label: String
        get() = korui.text.text
        set(value) {korui.text.text = value}

    override var onPress: () -> Unit
        get() = korui.onPressedUp
        set(value) { korui.onPressedUp = onPress }
}