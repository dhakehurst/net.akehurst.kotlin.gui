package net.akehurst.kotlin.gui.korui.views

import com.soywiz.kds.get
import com.soywiz.kds.iterators.fastForEach
import com.soywiz.kds.iterators.fastForEachWithIndex
import com.soywiz.kmem.FBuffer
import com.soywiz.kmem.clamp
import com.soywiz.korag.AG
import com.soywiz.korge.bitmapfont.drawText
import com.soywiz.korge.html.Html
import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.view.Fonts
import com.soywiz.korge3d.*
import com.soywiz.korim.bitmap.Bitmaps
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFont
import com.soywiz.korma.geom.*

class Text3D(

) : UIWidget() {

    companion object {
        val fonts = Fonts.fonts
    }

    var text = "A"
    set(value) {
        field = value
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

    init {
        recalculateBounds()
    }

    private fun recalculateBounds() {
        fonts.getBounds(text, format, out = textBounds)
    }

    private fun recalculateBoundsWhenRequired() {
        if (autoSize) recalculateBounds()
    }

    override fun render(ctx: RenderContext3D) {
        val ag = ctx.ag
        val font = fonts.getBitmapFont(format)


        font.drawText3D(
                ctx,1.0,text
        )
    }

    fun old(ctx: RenderContext3D) {
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
                ctx.rctx,
                format.computedSize.toDouble(),
                text,
                px.toInt(),
                py.toInt(),
                //m,
                colMul = RGBA.multiply(color, format.computedColor)
                //colAdd = colorAdd,
                //blendMode = renderBlendMode,
                //filtering = filtering
        )
    }
}