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
import com.soywiz.korim.format.readBitmap
import com.soywiz.korim.format.readNativeImage
import com.soywiz.korio.async.launch
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.async.suspendTest
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import net.akehurst.kotlin.gui.core.GuiLayoutFactorySimple
import net.akehurst.kotlin.gui.core.guiLayout
import net.akehurst.kotlin.gui.core.guiWindow

import kotlin.test.Test
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class GuiTestCommon {
    /*
        fun updateEnv(name: String, value: String) {
            val env = System.getenv()
            val field: Field = env.javaClass.getDeclaredField("m")
            field.setAccessible(true)
            (field.get(env) as MutableMap<String,String>).put(name,value)
        }
    */
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
        //Thread.sleep(1000000)
    }


    @Test
    fun gw() = suspendTest {
        lateinit var s: Stage

        val gw = CreateDefaultGameWindow()
        gw.configure(500, 300, "Window", null, null)
        gw.loop {

        }
        // gw.addEventListener()
        gw.waitClose()

        //Thread.sleep(1000000)
    }

    @ExperimentalTime
    @Korge3DExperimental
    @Test
    fun korg3d() = suspendTest {

        //   updateEnv("FORCE_GLSL_VERSION", "330")
        //   updateEnv("DEBUG_GLSL", "true")

        lateinit var s: Stage
        val bmp = resourcesVfs.get("Australia.bmp").readBitmap()
        val leftImage = resourcesVfs.get("skybox/left.jpg").readNativeImage()
        val rightImage = resourcesVfs.get("skybox/right.jpg").readNativeImage()
        val topImage = resourcesVfs.get("skybox/top.jpg").readNativeImage()
        val bottomImage = resourcesVfs.get("skybox/bottom.jpg").readNativeImage()
        val backImage = resourcesVfs.get("skybox/back.jpg").readNativeImage()
        val frontImage = resourcesVfs.get("skybox/front.jpg").readNativeImage()
        val groundMap = HeightMapBitmap(bmp)
        val sunX = 0f
        val sunY = 30f
        val sunZ = 0f
        val skyBoxCubeMap = object : CubeMap {
            override val left = leftImage
            override val right = rightImage
            override val top = topImage
            override val bottom = bottomImage
            override val back = backImage
            override val front = frontImage
        }

        GlobalScope.launch {
            val kg = Korge { // this: Stage
                lateinit var cuboide: Shape3D
                val stage3DView = scene3D {
                    camera.setPosition(0f, 10f, 5f)

                    skyBox(skyBoxCubeMap)

                    val l = light().position(sunX, sunY, sunZ).setTo(Colors.LIGHTGOLDENRODYELLOW)
                    val light = shape3D {
                        material(
                                //emission = Material3D.LightColor(Colors.WHITE),
                                diffuse = Material3D.LightColor(Colors.LIGHTGOLDENRODYELLOW)
                        )
                        sphere(1f)
                    }.position(sunX, sunY, sunZ)
                    launchImmediately {
                        while (true) {
                            tween(time = 48.seconds) {
                                l.modelMat.identity().rotate((it * 360).degrees, 0.degrees, 0.degrees)
                                light.modelMat.identity().rotate((it * 360).degrees, 0.degrees, 0.degrees)
                            }
                        }
                    }

                    val ground = terrain(0f, 0f, 308f, 308f, groundMap, 0.000001f)

                    val triangle = shape3D {
                        material(
                                diffuse = Material3D.LightColor(RGBA(0xaa, 0, 0, 0xff))
                        )
                        faceTriangle(
                                Vector3D(-1f, 1f, 0f),
                                Vector3D(1f, 1f, 0f),
                                Vector3D(1f, -1f, 0f)
                        )
                    }.position(-3f, 3f, 0f)

                    val rectangle = shape3D {
                        material(
                                ambient = Material3D.LightColor(RGBA(0x00, 0x02, 0x00, 0xff)),
                                diffuse = Material3D.LightColor(RGBA(0x00, 0xaa, 0, 0xff))
                        )
                        faceRectangle(
                                Vector3D(-1f, 1f, 0f),
                                Vector3D(1f, 1f, 0f),
                                Vector3D(1f, -1f, 0f),
                                Vector3D(-1f, -1f, 0f)
                        )
                    }.position(3f, 3f, 0f)

                    cuboide = shape3D {
                        material(
                                ambient = Material3D.LightColor(RGBA(0x00, 0x00, 0x02, 0xff)),
                                diffuse = Material3D.LightColor(RGBA(0x00, 0x00, 0xaa, 0xff)),
                                specular = Material3D.LightColor(Colors.WHITE),
                                shininess = 0.5f
                        )
                        cuboid(2f, 1f, 0.2f)
                    }.position(3f, -3f, 0f)

                    val pyramidTriangleBase = shape3D {
                        material(
                                ambient = Material3D.LightColor(RGBA(0x00, 0x02, 0x02, 0xff)),
                                diffuse = Material3D.LightColor(RGBA(0x00, 0xaa, 0xaa, 0xff)),
                                specular = Material3D.LightColor(Colors.WHITE),
                                shininess = 0.5f
                        )
                        pyramidTriangleBase(
                                Vector3D(0f, 0.5f, 0f),
                                Vector3D(0.5f, -0.5f, 0.5f),
                                Vector3D(-0.5f, -0.5f, 0.5f),
                                Vector3D(0f, -0.5f, -0.5f)
                        )
                    }//.position(-3f, -3f, 0f)


                    val sphere = shape3D {
                        material(
                                ambient = Material3D.LightColor(RGBA(0x02, 0x02, 0x00, 0xff)),
                                diffuse = Material3D.LightColor(RGBA(0xaa, 0xaa, 0x00, 0xff)),
                                specular = Material3D.LightColor(RGBA(0xaa, 0xaa, 0x00, 0xff)),
                                shininess = 0.6f
                        )
                        sphere(1f, longitudeLines = 360, latitudeLines = 360)
                    }.position(-3f, 0f, 0f)
                    /*
                    val blob = shape3D {
                        material(
                                ambient = Material3D.LightColor(RGBA(0x02, 0x02, 0x00, 0xff)),
                                diffuse = Material3D.LightColor(RGBA(0xaa, 0xaa, 0x00, 0xff)),
                                specular = Material3D.LightColor(RGBA(0xaa, 0xaa, 0x00, 0xff)),
                                shininess = 0.6f
                        )
                        parametric(360, 360) { u, v ->
                            val x = cos(u)
                            val y = v
                            val z = sin(u)
                            Vector3D(x, y, z)
                        }
                    }
*/
                    launchImmediately {
                        while (true) {
                            tween(time = 16.seconds) {
                                pyramidTriangleBase.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                                cuboide.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                                sphere.modelMat.identity().rotate((it * 360).degrees, (it * 180).degrees, 0.degrees)
                            }
                        }
                    }

                }

                var cameraSpeed = 1f
                var angleSpeed = 1.degrees

                keys {
                    downNew(Key.UP) {
                        when {
                            it.shift -> stage3DView.stage3D.camera.forward(cameraSpeed, 1f)
                            it.alt -> stage3DView.stage3D.camera.pitchDown(angleSpeed, 1f)
                        }
                    }
                    downNew(Key.DOWN) {
                        when {
                            it.shift -> stage3DView.stage3D.camera.backwards(cameraSpeed, 1f)
                            it.alt -> stage3DView.stage3D.camera.pitchUp(angleSpeed, 1f)
                        }
                    }
                    downNew(Key.RIGHT) {
                        when {
                            it.shift -> stage3DView.stage3D.camera.strafeRight(cameraSpeed, 1f)
                            it.alt -> stage3DView.stage3D.camera.yawRight(angleSpeed, 1f)
                            it.meta -> stage3DView.stage3D.rotationY -= 1.degrees
                        }
                    }
                    downNew(Key.LEFT) {
                        when {
                            it.shift -> stage3DView.stage3D.camera.strafeLeft(cameraSpeed, 1f)
                            it.alt -> stage3DView.stage3D.camera.yawLeft(angleSpeed, 1f)
                            it.meta -> stage3DView.stage3D.rotationY += 1.degrees
                        }
                    }
                    downNew(Key.EQUAL) {

                    }
                    downNew(Key.MINUS) {

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
                                stage3DView.stage3D.camera.x -= dx / 100
                                stage3DView.stage3D.camera.y -= dy / 100
                            }
                            down && it.isAltDown -> {
                                val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                                val dy = it.lastPosGlobal.y - it.currentPosGlobal.y

                            }
                        }
                    }
                    up {
                        down = false
                    }
                }
            }
        }
        delay(10.minutes)
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
        //Thread.sleep(1000000)
    }

    @Test
    fun panel() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Test", 1280.0, 720.0) {
            panel() {
                width = 5.0
                height = 10.0
                depth = 1.0
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
        //Thread.sleep(1000000)
    }

    @Test
    fun text() {
        val factory = GuiFactoryKorui()
        val win = guiWindow(factory, "Test", 1280.0, 720.0) {
            panel {
                width = 10.0
                height = 5.0
                text("Hello World!") {
                    z = -2.0
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
        // Thread.sleep(1000000)
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
        // Thread.sleep(1000000)
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
        //  Thread.sleep(1000000)
    }

}