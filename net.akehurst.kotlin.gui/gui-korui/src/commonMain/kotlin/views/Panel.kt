package net.akehurst.kotlin.gui.korui.views

import com.soywiz.korma.geom.Vector3D

class Panel : UIContainer() {

    init {
        this.addChild(Shape3D{
            face(Vector3D(0f, +.5f, 0f))
            face(Vector3D(0f, -.5f, 0f))

            face(Vector3D(+.5f, 0f, 0f))
            face(Vector3D(-.5f, 0f, 0f))

            face(Vector3D(0f, 0f, +.5f))
            face(Vector3D(0f, 0f, -.5f))
        })
    }
}