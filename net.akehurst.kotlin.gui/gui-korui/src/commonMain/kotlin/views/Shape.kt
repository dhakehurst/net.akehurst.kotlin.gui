package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.view.Graphics
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.vector.VectorBuilder

class Shape(
        autoScaling: Boolean = true,
        val drawCommands: VectorBuilder.() -> Unit
) : Graphics(autoScaling) {

    var bgColor: RGBA = Colors.WHITE

    init {
        updateGraphics()
    }

    fun updateGraphics() {
        clear()
        beginFill(bgColor, 1.0)
        try {
            this@Shape.drawCommands.invoke(this)
        } finally {
            endFill()
        }
    }

}