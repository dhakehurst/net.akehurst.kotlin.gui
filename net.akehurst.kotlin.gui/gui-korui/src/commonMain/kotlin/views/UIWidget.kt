package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.view.Container
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

open class UIWidget(

) : Container() {

    override var width: Double = 100.0
    override var height: Double = 100.0

    var bgColor : RGBA = Colors.SLATEGRAY



}