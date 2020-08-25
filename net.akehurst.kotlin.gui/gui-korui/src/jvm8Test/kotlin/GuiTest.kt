package net.akehurst.kotlin.gui.korui

//import kotlinx.coroutines.launch
import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.mouse
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

                    val b1 = cube {
                        this.x = -5.0
                    }

                    val b2 = cube {
                        this.x = +5.0
                    }

                    launchImmediately {
                        while (true) {
                            tween(time = 16.seconds) {
                                //b1.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                            }
                        }
                    }

                }

                keys {
                    var xr = 0.0.degrees

                    downNew (Key.UP) {
                        s.stage3D.camera.y += 1.0
                    }
                    downNew(Key.DOWN) {
                        s.stage3D.camera.y -= 1.0
                    }
                    downNew(Key.RIGHT) {
                        when {
                            it.ctrl -> {
                                xr = xr.plus(1.0.degrees)
                                s.stage3D.rotation(y = xr)
                            }
                            else -> s.stage3D.camera.x -= 1.0
                        }
                    }
                    downNew(Key.LEFT) {
                        s.stage3D.camera.x += 1.0
                    }
                    downNew(Key.EQUAL) {
                        s.stage3D.camera.z += 1.0
                    }
                    downNew(Key.MINUS) {
                        s.stage3D.camera.z -= 1.0
                    }
                    downNew(Key.COMMA) {
                        xr = xr.plus(1.0.degrees)
                        s.stage3D.rotation(y = xr)
                    }
                    downNew(Key.PERIOD) {
                        xr = xr.plus((-1.0).degrees)
                        s.stage3D.rotation(y = xr)
                    }
                }

                mouse {
                    var xStart = 0.0
                    var xLast = 0.0
                    var down = false
                    down {
                        down = true
                    }
                    move {
                        when{
                            down && it.isShiftDown -> {
                                val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                                val dy = it.lastPosGlobal.y - it.currentPosGlobal.y
                                s.stage3D.camera.x -= dx/100
                                s.stage3D.camera.y -= dy/100
                            }
                        }
                    }
                    up {
                        down = false
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
                button("U") {//"Press Me") {
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