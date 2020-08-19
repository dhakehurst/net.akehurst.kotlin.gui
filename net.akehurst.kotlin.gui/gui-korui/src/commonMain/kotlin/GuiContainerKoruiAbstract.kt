package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import com.soywiz.korge3d.Container3D
import net.akehurst.kotlin.gui.api.GuiContainer
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.korui.views.UIContainer
import net.akehurst.kotlin.gui.korui.views.UIWidget

abstract class GuiContainerKoruiAbstract : GuiControlKoruiAbstract(), GuiContainer {

    //abstract override val korui: Container
    abstract override val korui3D: UIContainer

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlKoruiAbstract -> {
                korui3D.addChild(element.korui3D)
                content.add(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlKoruiAbstract -> {
                korui3D.removeChild(element.korui3D)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

}