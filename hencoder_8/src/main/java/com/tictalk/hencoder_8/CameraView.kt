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
    val PADDING = Utils.dp2px(100f)
    val IMAGE_WIDTH = Utils.dp2px(200f)

    var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        camera.setLocation(0f, 0f, Utils.getZFromCamera())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2)
        canvas?.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas?.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas?.rotate(flipRotation)
        canvas?.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2))
        canvas?.drawBitmap(Utils.getAvatar(resources, IMAGE_WIDTH.toInt()), PADDING, PADDING, paint)
        canvas?.restore()

        canvas?.save()
        canvas?.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2)
        canvas?.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas?.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas?.rotate(flipRotation)
        canvas?.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2))
        canvas?.drawBitmap(Utils.getAvatar(resources, IMAGE_WIDTH.toInt()), PADDING, PADDING, paint)
        canvas?.restore()
    }
}