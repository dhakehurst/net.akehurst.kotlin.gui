package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korge3d.MeshBuilder3D
import com.soywiz.korge3d.ViewWithMesh3D
import com.soywiz.korma.geom.Matrix3D
import com.soywiz.korma.geom.Vector3D
import com.soywiz.korma.geom.scale

object Vector3DTemps {
    @PublishedApi
    internal var pos = 0

    @PublishedApi
    internal val items = arrayListOf<Vector3D>(Vector3D(), Vector3D(), Vector3D())

    fun alloc(): Vector3D {
        val npos = pos++
        return if (npos < items.size) {
            items[npos]
        } else {
            val item = Vector3D()
            items.add(item)
            item
        }
    }

    inline operator fun <T> invoke(callback: Vector3DTemps.() -> T): T {
        val oldPos = pos
        try {
            return callback()
        } finally {
            pos = oldPos
        }
    }

    operator fun Vector3D.plus(that: Vector3D) = alloc().setToFunc { this[it] + that[it] }
    operator fun Vector3D.minus(that: Vector3D) = alloc().setToFunc { this[it] - that[it] }
}

fun MeshBuilder3D.face(pos: Vector3D) {
    val dims = (0 until 3).filter { pos[it] == 0f }
    val normal = Vector3D().setToFunc { if (pos[it] != 0f) 1f else 0f }
    val dirs = Array(2) { dim -> Vector3D().setToFunc { if (it == dims[dim]) .5f else 0f } }
    val dx = dirs[0]
    val dy = dirs[1]
    Vector3DTemps {
        vertex(pos - dx - dy, normal, Vector3D(0f, 0f, 0f))
        vertex(pos + dx - dy, normal, Vector3D(1f, 0f, 0f))
        vertex(pos - dx + dy, normal, Vector3D(0f, 1f, 0f))

        vertex(pos - dx + dy, normal, Vector3D(0f, 1f, 0f))
        vertex(pos + dx - dy, normal, Vector3D(1f, 0f, 0f))
        vertex(pos + dx + dy, normal, Vector3D(1f, 1f, 0f))
    }
}

/*
 * Note: To draw solid quads, you can use [Bitmaps.white] + [AgBitmapTextureManager] as texture and the [colorMul] as quad color.
 */
class Shape3D(
        drawCommands: MeshBuilder3D.() -> Unit
) : ViewWithMesh3D(createMesh(drawCommands).copy()) {

    var width: Double = 1.0
    var height: Double = 1.0
    var depth: Double = 1.0

    override fun prepareExtraModelMatrix(mat: Matrix3D) {
        mat.identity().scale(width, height, depth)
    }

    companion object {

        fun createMesh(drawCommands: MeshBuilder3D.() -> Unit) = MeshBuilder3D {
            drawCommands()
        }
    }
}