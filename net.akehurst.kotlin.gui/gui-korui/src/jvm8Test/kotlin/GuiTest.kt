package net.akehurst.kotlin.gui.korui

import com.soywiz.klock.seconds
import com.soywiz.korge.Korge
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.roundRect
import com.soywiz.korge3d.Korge3DExperimental
import com.soywiz.korge3d.box
import com.soywiz.korge3d.scene3D
import com.soywiz.korgw.CreateDefaultGameWindow
import com.soywiz.korgw.configure
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.rotate
import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
import net.akehurst.kotlin.gui.core.GuiLayoutFactorySimple
import net.akehurst.kotlin.gui.core.guiLayout
import net.akehurst.kotlin.gui.core.guiWindow
import kotlin.test.Test

class GuiTest {

    @Test
    fun korg1() {
        lateinit var s: Stage
        GlobalScope.launch {
            val kg = Korge { // this: Stage
                s = this// ->
                roundRect(100.0, 100.0, 5.0, 5.0, Colors.BLUE)
            }
        }
        Thread.sleep(1000000)
    }


    @Test
    fun gw() {
        lateinit var s: Stage
        GlobalScope.launch {
            val gw = CreateDefaultGameWindow()
            gw.configure(500, 300, "Window", null, null)
            gw.loop {

            }
            // gw.addEventListener()
            gw.waitClose()
        }
        Thread.sleep(1000000)
    }

    @Korge3DExperimental
    @Test
    fun korg3d() {
        lateinit var s: Stage
        GlobalScope.launch {
            val kg = Korge { // this: Stage
                scene3D {
                   val b1 = box {

                    }

                    launchImmediately {
                        while (true) {
                            tween(time = 16.seconds) {
                                b1.modelMat.identity().rotate((it * 360).degrees, (it*180).degrees, 0.degrees)
                            }
                        }
                    }
                }

            }
        }
        Thread.sleep(1000000)
    }

    @Test
    fun button() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Demo Application",1280.0, 720.0 ) {
            panel {
                button("Press Me") {
                    onPressed {
                        println("pressed")
                    }
                    onReleased {
                        println("released")
                    }
                }
            }
        }
        win.applyLayout(
                guiLayout(GuiLayoutFactorySimple()) {
                    rule("*") {
                        declaration("display", "flex")
                    }
                }
        )
        win.show()
        Thread.sleep(1000000)
    }

}