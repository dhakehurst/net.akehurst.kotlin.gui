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
import com.soywiz.korim.color.RGBA
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korma.geom.Vector3D
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.plus
import com.soywiz.korma.geom.unaryMinus
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
                    light().position(0, 10, +10).setTo(Colors.WHITE)

                    //val b1 = cube {
                    //    this.x = -5.0
                    //}

                    //val b2 = cube {
                    //    this.x = +5.0
                    //}

                    val triangle = shape3D {
                        material(
                                diffuse = Material3D.LightColor(RGBA(0xaa, 0, 0, 0xff))
                        )
                        faceTriangle(
                                Vector3D(-1f, 1f, 0f),
                                Vector3D(1f, 1f, 0f),
                                Vector3D(1f, -1f, 0f)
                        )
                        /*
                        faceRectangle(
                                Vector3D(-5f, 5f, 0f),
                                Vector3D(5f, 5f, 0f),
                                Vector3D(5f, -5f, 0f),
                                Vector3D(-5f, -5f, 0f)
                        )

                         */
                        //cuboid(1f,1f,1f)

                    }.position(-3f,3f,0f)

                    val rectangle = shape3D {
                        material(
                                diffuse = Material3D.LightColor(RGBA(0x00, 0xaa, 0, 0xff))
                        )

                        faceRectangle(
                                Vector3D(-1f, 1f, 0f),
                                Vector3D(1f, 1f, 0f),
                                Vector3D(1f, -1f, 0f),
                                Vector3D(-1f, -1f, 0f)
                        )

                        //position(5f,5f,0f)
                    }.position(3f,3f,0f)

                    val cuboide = shape3D {
                        material(
                                diffuse = Material3D.LightColor(RGBA(0x00, 0x00, 0xaa, 0xff)),
                                specular = Material3D.LightColor(Colors.WHITE),
                                shininess = 0.5f
                        )

                        cuboid(2f,1f,0.5f)

                        //position(5f,5f,0f)
                    }//.position(3f,-3f,0f)

                    launchImmediately {
                        while (true) {
                            tween(time = 16.seconds) {
                                //b1.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                            }
                        }
                    }

                }

                var xRotate = 0.0.degrees
                var yRotate = 0.0.degrees
                var zRotate = 0.0.degrees

                keys {
                    downNew(Key.UP) {
                        when {
                            it.shift -> s.stage3D.camera.y += 1.0
                            it.alt -> {
                                xRotate = xRotate.plus(1.0.degrees)
                                s.stage3D.rotation(x = xRotate, y = yRotate)
                            }
                        }
                    }
                    downNew(Key.DOWN) {
                        when {
                            it.shift -> s.stage3D.camera.y -= 1.0
                            it.alt -> {
                                xRotate = xRotate.plus(-(1.0).degrees)
                                s.stage3D.rotation(x = xRotate, y = yRotate)
                            }
                        }
                    }
                    downNew(Key.RIGHT) {
                        when {
                            it.shift -> s.stage3D.camera.x -= 1.0
                            it.alt -> {
                                yRotate = yRotate.plus(1.0.degrees)
                                s.stage3D.rotation(x = xRotate, y = yRotate)
                            }
                        }
                    }
                    downNew(Key.LEFT) {
                        when {
                            it.shift -> s.stage3D.camera.x += 1.0
                            it.alt -> {
                                yRotate = yRotate.plus((-1.0).degrees)
                                s.stage3D.rotation(x = xRotate, y = yRotate)
                            }
                        }
                    }
                    downNew(Key.EQUAL) {
                        s.stage3D.camera.z += 1.0
                    }
                    downNew(Key.MINUS) {
                        s.stage3D.camera.z -= 1.0
                    }
                }

                mouse {
                    var down = false
                    down {
                        down = true
                    }
                    move {
                        when {
                            down && it.isShiftDown -> {
                                val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                                val dy = it.lastPosGlobal.y - it.currentPosGlobal.y
                                s.stage3D.camera.x -= dx / 100
                                s.stage3D.camera.y -= dy / 100
                            }
                            down && it.isAltDown -> {
                                val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                                val dy = it.lastPosGlobal.y - it.currentPosGlobal.y
                                xRotate = xRotate.plus((dy / 100).degrees)
                                yRotate = yRotate.plus(-(dx / 100).degrees)
                                s.stage3D.rotation(x = xRotate, y = yRotate)
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
    fun stage() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Test", 1280.0, 720.0) {
            val b1 = cube {
                this.x = -5.0
            }

            val b2 = cube {
                this.x = +5.0
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

    @Test
    fun panel() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Test", 1280.0, 720.0) {
            panel {
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

    @Test
    fun button() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Test", 1280.0, 720.0) {
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

    @Test
    fun windowsX2() {
        val factory = GuiFactoryKorui()
        val win1 = guiWindow(factory, "Test Win1", 1280.0, 720.0) {
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

        val win2 = guiWindow(factory, "Test Win2", 1280.0, 720.0) {
            panel {
                button("2") {//"Press Me") {
                    onPressed {
                        println("pressed")
                    }
                    onReleased {
                        println("released")
                    }
                }
            }
        }

        win1.applyLayout(
                guiLayout(GuiLayoutFactorySimple()) {
                    rule("*") {
                        declaration("display", "flex")
                        declaration("depth", "10.0")
                    }
                }
        )
        win1.show()
        win2.show()
        Thread.sleep(1000000)
    }

}