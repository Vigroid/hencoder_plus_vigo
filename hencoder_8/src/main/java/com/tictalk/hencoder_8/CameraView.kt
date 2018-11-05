package com.tictalk.hencoder_8

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val camera = Camera()

    init {
        camera.rotateX(45f)
        camera.setLocation(0f, 0f , Utils.getZFromCamera())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(100f + 400 / 2, 100f + 400 / 2)
        canvas?.clipRect(-400 / 2f, -400 / 2f, 400 / 2f, 0f)
        canvas?.translate(-(100f + 400 / 2), -(100f + 400 / 2))
        canvas?.drawBitmap(Utils.getAvatar(resources, 400), 100f, 100f, paint)
        canvas?.restore()

        canvas?.save()
        canvas?.translate(100f + 400 / 2, 100f + 400 / 2)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-400 / 2f, 0f, 400 / 2f, 400 / 2f)
        canvas?.translate(-(100f + 400 / 2), -(100f + 400 / 2))
        canvas?.drawBitmap(Utils.getAvatar(resources, 400), 100f, 100f, paint)
        canvas?.restore()
    }
}