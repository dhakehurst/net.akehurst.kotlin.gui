package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.View
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiLayout

abstract class GuiControlKoruiAbstract : GuiControl {

    abstract val korui : View

    // --- GuiControl ---

    override var width: Double
        get() = korui.width
        set(value) {korui.width = value}
    override var height: Double
        get() = korui.height
        set(value) {korui.height = value}

    override fun applyLayout(guiLayout: GuiLayout) {

    }
}