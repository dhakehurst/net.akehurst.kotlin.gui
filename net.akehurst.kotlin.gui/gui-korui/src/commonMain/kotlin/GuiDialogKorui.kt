package net.akehurst.kotlin.gui.korui

import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiDialog
import net.akehurst.kotlin.gui.korui.views.Dialog

class GuiDialogKorui : GuiDialog, GuiContainerKoruiAbstract() {

    override val korui = Dialog()


    // --- GuiDialog ---

    override var title: String = "Dialog"

    override fun show(modal: Boolean) {

    }

    override fun close() {

    }

}