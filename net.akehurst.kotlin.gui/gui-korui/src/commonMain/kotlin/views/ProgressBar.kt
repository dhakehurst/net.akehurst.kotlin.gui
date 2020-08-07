package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge.render.RenderContext
import com.soywiz.korge.render.TexturedVertexArray
import com.soywiz.korge.ui.DefaultUISkin
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.ninePatch

class ProgressBar : Container() {

    val rect = ninePatch(DefaultUISkin.normal, width, height, 10.0 / 64.0, 10.0 / 64.0, 54.0 / 64.0, 54.0 / 64.0)


}