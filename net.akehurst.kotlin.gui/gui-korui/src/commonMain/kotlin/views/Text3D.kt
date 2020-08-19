package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.bitmapfont.drawText
import com.soywiz.korge.html.Html
import com.soywiz.korge.view.Fonts
import com.soywiz.korge3d.MeshBuilder3D
import com.soywiz.korge3d.RenderContext3D
import com.soywiz.korge3d.View3D
import com.soywiz.korge3d.ViewWithMesh3D
import com.soywiz.korim.bitmap.Bitmaps
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Matrix3D
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.Vector3D
import com.soywiz.korma.geom.scale

class Text3D(

) : UIWidget() {

    companion object {
        val fonts = Fonts.fonts
    }

    var text = "Button"
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

    private fun recalculateBounds() {
        fonts.getBounds(text, format, out = textBounds)
    }

    private fun recalculateBoundsWhenRequired() {
        if (autoSize) recalculateBounds()
    }

    override fun render(ctx: RenderContext3D) {

        val font = fonts.getBitmapFont(format)
        val anchor = format.computedAlign.anchor
        fonts.getBounds(text, format, out = tempRect)
        //println("tempRect=$tempRect, textBounds=$textBounds")
        //tempRect.setToAnchoredRectangle(tempRect, format.align.anchor, textBounds)
        //val x = (textBounds.width) * anchor.sx - tempRect.width
        val px = textBounds.x + (textBounds.width - tempRect.width) * anchor.sx
        //val x = textBounds.x + (textBounds.width) * anchor.sx
        val py = textBounds.y + (textBounds.height - tempRect.height) * anchor.sy

        if (bgcolor.a != 0) {
            ctx.rctx.batch.drawQuad(
                    ctx.rctx.getTex(Bitmaps.white),
                    x = textBounds.x.toFloat(),
                    y = textBounds.y.toFloat(),
                    width = textBounds.width.toFloat(),
                    height = textBounds.height.toFloat(),
                   // m = m,
                    filtering = false,
                   colorMul = RGBA.multiply(bgcolor, color)
                   // colorAdd = colorAdd,
                   // blendFactors = renderBlendMode.factors
            )
        }

        //println(" -> ($x, $y)")
        font.drawText(
                ctx.rctx, format.computedSize.toDouble(), text, px.toInt(), py.toInt(),
                //m,
                colMul = RGBA.multiply(color, format.computedColor)
                //colAdd = colorAdd,
                //blendMode = renderBlendMode,
                //filtering = filtering
        )
    }
}