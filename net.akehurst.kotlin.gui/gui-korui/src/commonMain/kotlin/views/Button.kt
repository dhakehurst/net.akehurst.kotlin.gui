package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.input.mouse
import com.soywiz.korge.ui.DefaultUISkin
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.ninePatch
import com.soywiz.korge.view.text
import com.soywiz.korim.color.Colors

class Button : Container() {

    val text = text("",16.0, Colors.BLACK)
    val rect = ninePatch(DefaultUISkin.normal, width, height, 10.0 / 64.0, 10.0 / 64.0, 54.0 / 64.0, 54.0 / 64.0)

    var onOver = {}
    var onOut = {}
    var onPressedDown = {}
    var onPressedUp = {}

    init {
        mouse {
            onOver {
                onOver()
            }
            onOut {
                onOut()
            }
            onDown {
                onPressedDown()
            }
            onUpAnywhere {
                onPressedUp()
            }
        }
    }


}