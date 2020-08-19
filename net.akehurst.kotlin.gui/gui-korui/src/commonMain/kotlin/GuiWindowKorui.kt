package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.Korge
import com.soywiz.korge.view.RoundRect
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import kotlinx.coroutines.GlobalScope
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiWindow
import net.akehurst.kotlin.gui.korui.views.Window

class GuiWindowKorui : GuiWindow, GuiContainerKoruiAbstract() {

    private val window = Window()
    //override val korui = window.content
    override val korui3D = window.content3D

    override var title: String
        get() = TODO("not implemented")
        set(value) {
            window.title = value
        }

    override fun show() {
       window.show()
    }

}
