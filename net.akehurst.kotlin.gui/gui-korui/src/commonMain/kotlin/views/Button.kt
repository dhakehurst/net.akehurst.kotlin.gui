package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.input.mouse
import com.soywiz.korge.ui.DefaultUISkin
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.ninePatch
import com.soywiz.korge.view.roundRect
import com.soywiz.korge.view.text
import com.soywiz.korge3d.addTo
import com.soywiz.korge3d.cube
import com.soywiz.korim.color.Colors
import com.soywiz.korma.geom.vector.circle

class Button : UIWidget() {

    val block = cube {  }
    val text = Text3D().addTo(this)

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