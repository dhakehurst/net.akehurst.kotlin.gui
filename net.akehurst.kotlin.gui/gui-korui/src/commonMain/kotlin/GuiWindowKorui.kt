package net.akehurst.kotlin.gui.korui

import com.soywiz.korge.Korge
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import kotlinx.coroutines.GlobalScope
import net.akehurst.kotlin.gui.api.GuiControl
import net.akehurst.kotlin.gui.api.GuiWindow

class GuiWindowKorui : GuiWindow {

    private var stage: Stage? = null // will be null until the show() is called

    override var title: String
        get() = TODO("not implemented")
        set(value) {
        }

    // GuiContainer

    override val content: MutableList<GuiControl> = mutableListOf()

    override fun addContent(element: GuiControl) {
        when (element) {
            is GuiControlKorui -> {
                stage?.addChild(element.korui)
                content.add(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun removeContent(element: GuiControl) {
        when (element) {
            is GuiControlKorui -> {
                stage?.removeChild(element.korui)
                content.remove(element)
            }
            else -> error("$element is not a GuiControlKorui")
        }

    }

    override fun show() {
        GlobalScope.launch {
            Korge(
                    width = 100,
                    height = 100,
                    bgcolor = Colors["#aabbff"]
            ) {
                this@GuiWindowKorui.stage = this
                this@GuiWindowKorui.content.forEach {element ->
                    when (element) {
                        is GuiControlKorui -> {
                            stage.addChild(element.korui)
                        }
                        else -> error("$element is not a GuiControlKorui")
                    }
                }
            }
        }
    }
}
