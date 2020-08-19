package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.View
import net.akehurst.kotlin.gui.api.GuiContainer
import net.akehurst.kotlin.gui.api.GuiControl

abstract class GuiContainerKoruiAbstract : GuiControlKoruiAbstract(), GuiContainer {

    abstract override val korui: Container

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlKoruiAbstract -> {
                korui.addChild(element.korui)
                content.add(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlKoruiAbstract -> {
                korui.removeChild(element.korui)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

}