package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.bitmapfont.drawText
import com.soywiz.korge.html.Html
import com.soywiz.korge.view.Fonts
import com.soywiz.korge3d.RenderContext3D
import com.soywiz.korge3d.drawText3D
import com.soywiz.korge3d.text3D
import com.soywiz.korim.bitmap.Bitmaps
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.Vector3D

class Text3D(
) : UIWidget() {

    companion object {
        val fonts = Fonts.fonts
    }

    var text
        get() = text3D.text
        set(value) {
            text3D.text = value
            recalculateBoundsWhenRequired()
        }
    var color = Colors.WHITE
    var bgcolor = Colors.TRANSPARENT_BLACK
    var autoSize = true
        set(value) {
            field = value
            recalculateBoundsWhenRequired()
        }
    val textBounds = Rectangle(0, 0, 1024, 1024)
    private val tempRect = Rectangle()

    private var _format: Html.Format = Html.Format()
    var format: Html.Format
        get() = _format
        set(value) {
            _format = value
            recalculateBoundsWhenRequired()
        }

    val text3D = text3D(
            "Temp",
            Vector3D(x,y,-1.0),
            Vector3D(x,y,-1.0),
            Vector3D(x,y,-1.0),
            Vector3D(x,y,-1.0)
    )

    init {
        recalculateBounds()
    }

    private fun recalculateBounds() {
        fonts.getBounds(text, format, out = textBounds)
    }

    private fun recalculateBoundsWhenRequired() {
        if (autoSize) recalculateBounds()
    }



}