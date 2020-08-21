package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.view.Container
import com.soywiz.korge3d.Container3D
import com.soywiz.korge3d.RenderContext3D
import com.soywiz.korge3d.View3D
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA

open class UIWidget(

) : Container3D() {

    open var width: Double = 1.0
    open var height: Double = 1.0
    open var depth: Double = 1.0

    var faceColor : RGBA = Colors.RED


}