package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.View
import com.soywiz.korge3d.View3D
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiLayout
import net.akehurst.kotlin.gui.korui.views.UIWidget

abstract class GuiControlKoruiAbstract : GuiControl {

    //abstract val korui : View
    abstract val korui3D : UIWidget

    // --- GuiControl ---

    override var width: Double
        get() = korui3D.width
        set(value) {korui3D.width = value}

    override var height: Double
        get() = korui3D.height
        set(value) {korui3D.height = value}

    override var depth: Double
        get() = korui3D.depth
        set(value) {korui3D.depth = value}

    override fun applyLayout(guiLayout: GuiLayout) {

    }
}