package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.input.mouse
import com.soywiz.korge.ui.DefaultUISkin
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.ninePatch
import com.soywiz.korge.view.roundRect
import com.soywiz.korge.view.text
import com.soywiz.korge3d.addTo
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.vector.circle

class Button : UIWidget() {

    //val rect = roundRect(width, height,5.0,5.0,Colors.SLATEGRAY)
    val text = Text3D().addTo(this)
//    val rect = ninePatch(DefaultUISkin.normal, width, height, 10.0 / 64.0, 10.0 / 64.0, 54.0 / 64.0, 54.0 / 64.0)

    var onOver = {}
    var onOut = {}
    var onPressed = {}
    var onReleased = {}
/*
    init {
        mouse {
            onOver {
                onOver()
            }
            onOut {
                onOut()
            }
            onDown {
                onPressed()
            }
            onUp {
                onReleased()
            }
        }

    }
*/

}