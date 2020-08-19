package net.akehurst.kotlin.gui.jfx

import javafx.scene.Parent
import javafx.scene.layout.Pane
import net.akehurst.kotlin.gui.api.GuiContainer
import net.akehurst.kotlin.gui.api.GuiControl

abstract class GuiContainerJfxAbstract : GuiControlJfxAbstract(), GuiContainer {

    abstract override val jfx: Pane

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlJfxAbstract -> {
                jfx.children.add(element.jfx)
                content.add(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlJfxAbstract -> {
                jfx.children.remove(element.jfx)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

}