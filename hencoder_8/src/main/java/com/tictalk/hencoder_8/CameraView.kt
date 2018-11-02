package com.tictalk.henplus_8.view

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
        canvas?.translate(300f+100f, 300f+100f)
        canvas?.clipRect(-300f,-300f,300f, 0f)
        canvas?.translate(-(100f+300f), -(100f+300f))
        canvas?.drawBitmap(Utils.getAvatar(resources, 600), 100f, 100f, paint)
        canvas?.restore()

        //下部分
        canvas?.save()
        canvas?.translate(300f+100f, 300f+100f)
        camera.applyToCanvas(canvas)
        //3维变化前切割
        canvas?.clipRect(-300f,0f,300f,300f)
        canvas?.translate(-(100f+300f), -(100f+300f))
        canvas?.drawBitmap(Utils.getAvatar(resources, 600), 100f, 100f, paint)
        canvas?.restore()
    }
}