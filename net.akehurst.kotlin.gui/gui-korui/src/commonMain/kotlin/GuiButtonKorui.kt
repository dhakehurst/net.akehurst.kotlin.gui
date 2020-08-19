package net.akehurst.kotlin.gui.korui

import net.akehurst.kotlin.gui.api.GuiButton
import net.akehurst.kotlin.gui.korui.views.Button

class GuiButtonKorui : GuiButton, GuiControlKoruiAbstract() {

    override val korui3D = Button()

    override var label: String
        get() = korui3D.text.text
        set(value) {korui3D.text.text = value}

    override var onPressed: () -> Unit
        get() = korui3D.onPressed
        set(value) { korui3D.onPressed = value }

    override var onReleased: () -> Unit
        get() = korui3D.onReleased
        set(value) { korui3D.onReleased = value }


}