package net.akehurst.kotlin.gui.korui

//import kotlinx.coroutines.launch
import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korev.keys
import com.soywiz.korev.mouse
import com.soywiz.korge.Korge
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.Stage
import com.soywiz.korge.view.roundRect
import com.soywiz.korge3d.*
import com.soywiz.korgw.CreateDefaultGameWindow
import com.soywiz.korgw.configure
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.plus
import com.soywiz.korma.geom.rotate
import kotlinx.coroutines.GlobalScope
import net.akehurst.kotlin.gui.core.GuiLayoutFactorySimple
import net.akehurst.kotlin.gui.core.guiLayout
import net.akehurst.kotlin.gui.core.guiWindow
import kotlin.test.Test

class GuiTest {

    @Test
    fun korg1() {
        var rpy = 0
        lateinit var s: Stage
        GlobalScope.launch {
            val kg = Korge { // this: Stage
                s = this// ->
                val r = roundRect(100.0, 100.0, 5.0, 5.0, Colors.BLUE)

                keys {
                    this.down(Key.UP) {
                        r.y--
                    }
                    down(Key.DOWN) {
                        r.y++
                    }
                }
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
                val s = scene3D {
                    light().position(0, 10, +10).setTo(Colors.RED)

                    val b1 = box {

                    }

                    launchImmediately {
                        while (true) {
                            tween(time = 16.seconds) {
                              //  b1.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                            }
                        }
                    }

                }
                var ctrl = false
                keys {
                    var xr = 0.0.degrees

                    down(Key.LEFT_CONTROL) {       ctrl = true  }
                    down(Key.RIGHT_CONTROL) { ctrl = true }
                    up(Key.LEFT_CONTROL) { ctrl = false }
                    up(Key.RIGHT_CONTROL) { ctrl = false }
                    down(Key.UP) {
                        s.stage3D.camera.y += 1.0
                    }
                    down(Key.DOWN) {
                        s.stage3D.camera.y -= 1.0
                    }
                    down(Key.RIGHT) {
                        when {
                            ctrl -> {
                                xr = xr.plus(1.0.degrees)
                                s.stage3D.camera.modelMat.rotate(xr, 0.0.degrees, 0.0.degrees)
                            }
                            else -> s.stage3D.camera.x -= 1.0
                        }
                    }
                    down(Key.LEFT) {
                        s.stage3D.camera.x += 1.0
                    }
                    down(Key.EQUAL) {
                        s.stage3D.camera.z += 1.0
                    }
                    down(Key.MINUS) {
                        s.stage3D.camera.z -= 1.0
                    }

                }

                mouse {
                    var xStart = 0
                    var xLast = 0
                    var down = false
                    down {
                        down = true
                        xStart = this.x
                    }
                    move {
                        if (down) {
                            //TODO: detect change direction
                            val dx = this.x - xStart

                            when {
                                isCtrlDown -> {
                                }
                                isShiftDown -> {
                                }
                                else -> {
                                    s.stage3D.camera.x += dx/100.0
                                }

                            }
                            xLast = this.x
                        }
                    }
                    up {
                        down = false
                        xStart = 0
                    }
                }
            }
        }
        Thread.sleep(1000000)
    }

    @Test
    fun button() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Demo Application", 1280.0, 720.0) {
            panel {
                button("P") {//"Press Me") {
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
                        declaration("depth", "10.0")
                    }
                }
        )
        win.show()
        Thread.sleep(1000000)
    }

}