package net.akehurst.kotlin.gui.korui

import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiDialog
import net.akehurst.kotlin.gui.korui.views.Dialog

class GuiDialogKorui : GuiDialog, GuiControlKorui {

    override val korui = Dialog()


    // --- GuiContainer ---

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlKorui -> {
                korui.addChild(element.korui)
                content.add(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlKorui -> {
                korui.removeChild(element.korui)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }


    // --- GuiDialog ---

    override var title: String = "Dialog"

    override fun show(modal: Boolean) {

    }
}