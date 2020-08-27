package net.akehurst.kotlin.gui.korui

import com.soywiz.korge3d.cube
import net.akehurst.kotlin.gui.api.GuiCube
import net.akehurst.kotlin.gui.korui.views.UIContainer

class GuiCubeKorui : GuiContainerKoruiAbstract(), GuiCube {

    override val korui3D = UIContainer()
    val shape = korui3D.cube {  }

    override var x: Double
        get() = shape.x
        set(value) { shape.x = value}

    override var y: Double
        get() = shape.y
        set(value) { shape.y = value}

    override var z: Double
        get() = shape.z
        set(value) { shape.z = value}
}