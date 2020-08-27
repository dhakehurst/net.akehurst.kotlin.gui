package net.akehurst.kotlin.gui.korui.views

import com.soywiz.kds.iterators.fastForEach
import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeProvider
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.hr.HRTimeProvider
import com.soywiz.klock.milliseconds
import com.soywiz.korev.*
import com.soywiz.korge.Korge
import com.soywiz.korge.input.Input
import com.soywiz.korge.input.keys
import com.soywiz.korge.input.mouse
import com.soywiz.korge.internal.KorgeInternal
import com.soywiz.korge.logger.configureLoggerFromProperties
import com.soywiz.korge.resources.ResourcesRoot
import com.soywiz.korge.scene.EmptyScene
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.stat.Stats
import com.soywiz.korge.time.toTimeProvider
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Views
import com.soywiz.korge.view.addTo
import com.soywiz.korge3d.*
import com.soywiz.korgw.CreateDefaultGameWindow
import com.soywiz.korgw.GameWindow
import com.soywiz.korgw.configure
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.format.*
import com.soywiz.korim.vector.SizedDrawable
import com.soywiz.korim.vector.render
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korinject.AsyncInjectorContext
import com.soywiz.korio.async.delay
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.dynamic.KDynamic
import com.soywiz.korio.file.std.localCurrentDirVfs
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.lang.printStackTrace
import com.soywiz.korio.util.OS
import com.soywiz.korma.geom.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass

class CameraConfiguration {
    var keyZoomIn = Key.EQUAL
    var keyZoomOut = Key.MINUS
}

class WindowConfiguration {
    val camera = CameraConfiguration()
    var initialX = 0.0
    var initialY = 0.0
    var initialZ = -50.0
}

class Window(
        var title: String = "Korge",
        val width: Int = 1280,
        val height: Int = 720,
        val virtualWidth: Int = width,
        val virtualHeight: Int = height,
        val icon: Bitmap? = null,
        val iconPath: String? = null,
        val iconDrawable: SizedDrawable? = null,
        val imageFormats: ImageFormat = ImageFormats(PNG),
        val quality: GameWindow.Quality = GameWindow.Quality.AUTOMATIC,
        val targetFps: Double = 0.0,
        val scaleAnchor: Anchor = Anchor.MIDDLE_CENTER,
        val scaleMode: ScaleMode = ScaleMode.SHOW_ALL,
        val clipBorders: Boolean = true,
        val bgcolor: RGBA = Colors.BLACK,
        val debug: Boolean = false,
        val fullscreen: Boolean? = null,
        val args: Array<String> = arrayOf(),
        val gameWindow: GameWindow? = null,
        val timeProvider: HRTimeProvider = HRTimeProvider,
        val injector: AsyncInjector = AsyncInjector()
) {

    val configuration = WindowConfiguration()

    var isVisible = false

    val content = object : Container() {
        override var width: Double = this@Window.width.toDouble()
        override var height: Double = this@Window.height.toDouble()
    }
    val stage3DView = content.scene3D {
        light().position(0, 10, +10).setTo(Colors.RED)
    }
    val content3D = UIContainer().addTo(stage3DView.stage3D)

    suspend fun start() {
        val window = this

        stage3DView.stage3D.camera.x = configuration.initialX
        stage3DView.stage3D.camera.y = configuration.initialY
        stage3DView.stage3D.camera.z = configuration.initialZ

        if (!OS.isJsBrowser) {
            configureLoggerFromProperties(localCurrentDirVfs["klogger.properties"])
        }
        val realGameWindow = (window.gameWindow ?: coroutineContext[GameWindow] ?: CreateDefaultGameWindow())
        realGameWindow.loop {
            val gameWindow = this
            if (OS.isNative) println("Korui[0]")
            realGameWindow.configure(window.width, window.height, window.title, window.icon, window.fullscreen)
            try {
                // Do nothing
                when {
                    window.iconDrawable != null -> this.icon = window.iconDrawable.render()
                    window.iconPath != null -> this.icon = resourcesVfs[window.iconPath].readBitmapOptimized(window.imageFormats)
                    else -> Unit
                }
            } catch (e: Throwable) {
                Korge.logger.error { "Couldn't get the application icon" }
                e.printStackTrace()
            }
            this.quality = window.quality
            if (OS.isNative) println("CanvasApplicationEx.IN[0]")
            val input = Input()
            val stats = Stats()

            // Use this once Korgw is on 1.12.5
            //val views = Views(gameWindow.getCoroutineDispatcherWithCurrentContext() + SupervisorJob(), ag, injector, input, timeProvider, stats, gameWindow)
            val views = Views(coroutineContext + gameWindow.coroutineDispatcher + AsyncInjectorContext(window.injector) + SupervisorJob(), ag, window.injector, input, window.timeProvider, stats, gameWindow)

            if (OS.isJsBrowser) KDynamic { global["views"] = views }
            window.injector
                    .mapInstance(Korge.ModuleArgs(window.args))
                    .mapInstance(GameWindow::class, gameWindow)
                    .mapInstance<Module>(object : Module() {
                        override val title = window.title
                        override val fullscreen: Boolean? = window.fullscreen
                        override val windowSize = SizeInt(window.width, window.height)
                        override val size = SizeInt(window.virtualWidth, window.virtualHeight)
                    })
            views.debugViews = window.debug
            views.setVirtualSize(window.virtualWidth, window.virtualHeight)
            views.scaleAnchor = window.scaleAnchor
            views.scaleMode = window.scaleMode
            views.clipBorders = window.clipBorders
            views.targetFps = window.targetFps

            Korge.prepareViews(views, gameWindow, window.bgcolor != null, window.bgcolor ?: Colors.TRANSPARENT_BLACK, waitForFirstRender = true)

            views.launchImmediately {
                coroutineScope {
                    content.addTo(views.stage)
                }
            }
            if (OS.isNative) println("CanvasApplicationEx.IN[1]")
            if (OS.isNative) println("Korui[1]")

            var xRotate = 0.0.degrees
            var yRotate = 0.0.degrees
            var zRotate = 0.0.degrees

            views.stage.keys {
                downNew(Key.UP) {
                    when {
                        it.shift -> stage3DView.stage3D.camera.y += 1.0
                        it.alt -> {
                            xRotate = xRotate.plus(1.0.degrees)
                            stage3DView.stage3D.rotation(x = xRotate, y = yRotate)
                        }
                    }
                }
                downNew(Key.DOWN) {
                    when {
                        it.shift -> stage3DView.stage3D.camera.y -= 1.0
                        it.alt -> {
                            xRotate = xRotate.plus(-(1.0).degrees)
                            stage3DView.stage3D.rotation(x = xRotate, y = yRotate)
                        }
                    }
                }
                downNew(Key.RIGHT) {
                    when {
                        it.shift -> stage3DView.stage3D.camera.x -= 1.0
                        it.alt -> {
                            yRotate = yRotate.plus(1.0.degrees)
                            stage3DView.stage3D.rotation(x = xRotate, y = yRotate)
                        }
                    }
                }
                downNew(Key.LEFT) {
                    when {
                        it.shift -> stage3DView.stage3D.camera.x += 1.0
                        it.alt -> {
                            yRotate = yRotate.plus((-1.0).degrees)
                            stage3DView.stage3D.rotation(x = xRotate, y = yRotate)
                        }
                    }
                }
                downNew(configuration.camera.keyZoomIn) {
                    stage3DView.stage3D.camera.z += 1.0
                }
                downNew(configuration.camera.keyZoomOut) {
                    stage3DView.stage3D.camera.z -= 1.0
                }
            }

            views.stage.mouse {
                var down = false
                down {
                    down = true
                }
                move {
                    when{
                        down && it.isShiftDown -> {
                            val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                            val dy = it.lastPosGlobal.y - it.currentPosGlobal.y
                            stage3DView.stage3D.camera.x -= dx/100
                            stage3DView.stage3D.camera.y -= dy/100
                        }
                        down && it.isAltDown -> {
                            val dx = it.lastPosGlobal.x - it.currentPosGlobal.x
                            val dy = it.lastPosGlobal.y - it.currentPosGlobal.y
                            xRotate = xRotate.plus((dy/100).degrees)
                            yRotate = yRotate.plus(-(dx/100).degrees)
                            stage3DView.stage3D.rotation(x = xRotate, y = yRotate)
                        }
                    }
                }
                up {
                    down = false
                }
            }
        }
    }

    fun show(async:Boolean = true) {
        if (async) {
            GlobalScope.async {
                start()
            }
        } else {
            GlobalScope.launch {
                start()
            }
        }
    }

    suspend fun waitForShow() {
        while (isVisible.not()) {
            delay(100.milliseconds)
        }
    }

    suspend fun GameWindow.waitClose() {
        while (running) {
            delay(100.milliseconds)
        }
    }

    @KorgeInternal
    fun prepareViewsBase(
            views: Views,
            eventDispatcher: EventDispatcher,
            clearEachFrame: Boolean = true,
            bgcolor: RGBA = Colors.TRANSPARENT_BLACK,
            fixedSizeStep: TimeSpan = TimeSpan.NIL
    ): CompletableDeferred<Unit> {
        val injector = views.injector
        injector.mapInstance(views)
        injector.mapInstance(views.ag)
        injector.mapSingleton(ResourcesRoot::class) { ResourcesRoot() }
        injector.mapInstance(views.input)
        injector.mapInstance(views.stats)
        injector.mapInstance(CoroutineContext::class, views.coroutineContext)
        injector.mapPrototype(EmptyScene::class) { EmptyScene() }
        injector.mapInstance(TimeProvider::class, views.timeProvider.toTimeProvider()) // Deprecated
        injector.mapInstance(HRTimeProvider::class, views.timeProvider)

        val input = views.input
        val ag = views.ag
        val downPos = Point()
        val upPos = Point()
        var downTime = DateTime.EPOCH
        var moveTime = DateTime.EPOCH
        var upTime = DateTime.EPOCH
        var moveMouseOutsideInNextFrame = false
        val mouseTouchId = -1

        // devicePixelRatio might change at runtime by changing the resolution or changing the screen of the window
        fun getRealX(x: Double, scaleCoords: Boolean) = if (scaleCoords) x * ag.devicePixelRatio else x
        fun getRealY(y: Double, scaleCoords: Boolean) = if (scaleCoords) y * ag.devicePixelRatio else y

        fun updateTouch(id: Int, x: Double, y: Double, start: Boolean, end: Boolean) {
            val touch = input.getTouch(id)
            val now = DateTime.now()

            touch.id = id
            touch.active = !end

            if (start) {
                touch.startTime = now
                touch.start.setTo(x, y)
            }

            touch.currentTime = now
            touch.current.setTo(x, y)

            input.updateTouches()
        }

        fun mouseDown(type: String, x: Double, y: Double) {
            views.input.mouseButtons = 1
            views.input.mouse.setTo(x, y)
            views.mouseUpdated()
            downPos.copyFrom(views.input.mouse)
            downTime = DateTime.now()
            views.input.mouseInside = true
        }

        fun mouseUp(type: String, x: Double, y: Double) {
            //Console.log("mouseUp: $name")
            views.input.mouseButtons = 0
            views.input.mouse.setTo(x, y)
            views.mouseUpdated()
            upPos.copyFrom(views.input.mouse)

            if (type == "onTouchEnd") {
                upTime = DateTime.now()
                if ((downTime - upTime) <= 40.milliseconds) {
                    //Console.log("mouseClick: $name")
                    views.dispatch(MouseEvent(MouseEvent.Type.CLICK))
                }
            }
        }

        fun mouseMove(type: String, x: Double, y: Double, inside: Boolean) {
            views.input.mouse.setTo(x, y)
            views.input.mouseInside = inside
            if (!inside) {
                moveMouseOutsideInNextFrame = true
            }
            views.mouseUpdated()
            moveTime = DateTime.now()
        }

        fun mouseDrag(type: String, x: Double, y: Double) {
            views.input.mouse.setTo(x, y)
            views.mouseUpdated()
            moveTime = DateTime.now()
        }

        eventDispatcher.addEventListener<MouseEvent> { e ->
            //println("MOUSE: $e")
            Korge.logger.trace { "eventDispatcher.addEventListener<MouseEvent>:$e" }
            val x = getRealX(e.x.toDouble(), e.scaleCoords)
            val y = getRealY(e.y.toDouble(), e.scaleCoords)
            when (e.type) {
                MouseEvent.Type.DOWN -> {
                    mouseDown("mouseDown", x, y)
                    updateTouch(mouseTouchId, x, y, start = true, end = false)
                }
                MouseEvent.Type.UP -> {
                    mouseUp("mouseUp", x, y)
                    updateTouch(mouseTouchId, x, y, start = false, end = true)
                }
                MouseEvent.Type.DRAG -> {
                    mouseDrag("onMouseDrag", x, y)
                    updateTouch(mouseTouchId, x, y, start = false, end = false)
                }
                MouseEvent.Type.MOVE -> mouseMove("mouseMove", x, y, inside = true)
                MouseEvent.Type.CLICK -> Unit
                MouseEvent.Type.ENTER -> mouseMove("mouseEnter", x, y, inside = true)
                MouseEvent.Type.EXIT -> mouseMove("mouseExit", x, y, inside = false)
                MouseEvent.Type.SCROLL -> Unit
            }
            views.dispatch(e)
        }

        eventDispatcher.addEventListener<KeyEvent> { e ->
            Korge.logger.trace { "eventDispatcher.addEventListener<KeyEvent>:$e" }
            views.dispatch(e)
        }

        eventDispatcher.addEventListener<ResumeEvent> { e -> views.dispatch(e) }
        eventDispatcher.addEventListener<PauseEvent> { e -> views.dispatch(e) }
        eventDispatcher.addEventListener<StopEvent> { e -> views.dispatch(e) }
        eventDispatcher.addEventListener<DestroyEvent> { e ->
            try {
                views.dispatch(e)
            } finally {
                views.launchImmediately {
                    views.close()
                }
            }
        }


        // TOUCH
        fun touch(e: TouchEvent, start: Boolean, end: Boolean) {
            val t = e.touches.first()
            val x = t.current.x
            val y = t.current.y
            updateTouch(t.id, x, y, start, end)
            when {
                start -> {
                    mouseDown("onTouchStart", x, y)
                }
                end -> {
                    mouseUp("onTouchEnd", x, y)
                    moveMouseOutsideInNextFrame = true
                }
                else -> {
                    mouseMove("onTouchMove", x, y, inside = true)
                }
            }
        }

        eventDispatcher.addEventListener<TouchEvent> { e ->
            Korge.logger.trace { "eventDispatcher.addEventListener<TouchEvent>:$e" }
            val touch = e.touches.first()
            val ix = getRealX(touch.current.x, e.scaleCoords).toInt()
            val iy = getRealX(touch.current.y, e.scaleCoords).toInt()
            when (e.type) {
                TouchEvent.Type.START -> {
                    touch(e, start = true, end = false)
                    views.dispatch(MouseEvent(MouseEvent.Type.DOWN, 0, ix, iy, MouseButton.LEFT, 1))
                }
                TouchEvent.Type.MOVE -> {
                    touch(e, start = false, end = false)
                    views.dispatch(MouseEvent(MouseEvent.Type.DRAG, 0, ix, iy, MouseButton.LEFT, 1))
                }
                TouchEvent.Type.END -> {
                    touch(e, start = false, end = true)
                    views.dispatch(MouseEvent(MouseEvent.Type.UP, 0, ix, iy, MouseButton.LEFT, 0))
                    //println("DISPATCH MouseEvent(MouseEvent.Type.UP)")
                }
            }
            views.dispatch(e)
        }

        fun gamepadUpdated(e: GamePadUpdateEvent) {
            e.gamepads.fastForEach { gamepad ->
                input.gamepads[gamepad.index].copyFrom(gamepad)
            }
            input.updateConnectedGamepads()
        }

        eventDispatcher.addEventListener<GamePadConnectionEvent> { e ->
            Korge.logger.trace { "eventDispatcher.addEventListener<GamePadConnectionEvent>:$e" }
            views.dispatch(e)
        }

        eventDispatcher.addEventListener<GamePadUpdateEvent> { e ->
            gamepadUpdated(e)
            views.dispatch(e)
        }

        eventDispatcher.addEventListener<ReshapeEvent> { e ->
            //try { throw Exception() } catch (e: Throwable) { e.printStackTrace() }
            //println("eventDispatcher.addEventListener<ReshapeEvent>: ${ag.backWidth}x${ag.backHeight} : ${e.width}x${e.height}")
            views.resized(ag.backWidth, ag.backHeight)
        }

        //println("eventDispatcher.dispatch(ReshapeEvent(0, 0, views.nativeWidth, views.nativeHeight)) : ${views.nativeWidth}x${views.nativeHeight}")
        eventDispatcher.dispatch(ReshapeEvent(0, 0, views.nativeWidth, views.nativeHeight))

        var renderShown = false
        views.clearEachFrame = clearEachFrame
        views.clearColor = bgcolor
        val firstRenderDeferred = CompletableDeferred<Unit>()
        views.gameWindow.addEventListener<RenderEvent> {
            //println("RenderEvent: $it")
            if (!renderShown) {
                //println("!!!!!!!!!!!!! views.gameWindow.addEventListener<RenderEvent>")
                renderShown = true
                firstRenderDeferred.complete(Unit)
            }
            try {
                views.frameUpdateAndRender(fixedSizeStep = fixedSizeStep)

                if (moveMouseOutsideInNextFrame) {
                    moveMouseOutsideInNextFrame = false
                    views.input.mouseInside = false
                    views.mouseUpdated()
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

        return firstRenderDeferred
    }

    @KorgeInternal
    suspend fun prepareViews(
            views: Views,
            eventDispatcher: EventDispatcher,
            clearEachFrame: Boolean = true,
            bgcolor: RGBA = Colors.TRANSPARENT_BLACK,
            fixedSizeStep: TimeSpan = TimeSpan.NIL,
            waitForFirstRender: Boolean = true
    ) {
        val firstRenderDeferred = prepareViewsBase(views, eventDispatcher, clearEachFrame, bgcolor, fixedSizeStep)
        if (waitForFirstRender) {
            firstRenderDeferred.await()
        }
    }

    data class Config(
            val module: Module,
            val args: Array<String> = arrayOf(),
            val imageFormats: ImageFormat = RegisteredImageFormats,
            val gameWindow: GameWindow? = null,
            //val eventDispatcher: EventDispatcher = gameWindow ?: DummyEventDispatcher, // Removed
            val sceneClass: KClass<out Scene> = module.mainScene,
            val sceneInjects: List<Any> = listOf(),
            val timeProvider: HRTimeProvider = HRTimeProvider,
            val injector: AsyncInjector = AsyncInjector(),
            val debug: Boolean = false,
            val trace: Boolean = false,
            val context: Any? = null,
            val fullscreen: Boolean? = null,
            val constructedViews: (Views) -> Unit = {}
    )

    data class ModuleArgs(val args: Array<String>)
}