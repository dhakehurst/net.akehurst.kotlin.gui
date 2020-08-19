package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.view.View
import net.akehurst.kotlin.gui.api.GuiProgressBar
import net.akehurst.kotlin.gui.korui.views.ProgressBar

class GuiProgressBarKorui : GuiProgressBar, GuiControlKoruiAbstract() {

    override val korui = ProgressBar()

    override var progress: Double = 0.0



}