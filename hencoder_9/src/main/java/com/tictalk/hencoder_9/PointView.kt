package com.tictalk.hencoder_9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class PointView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var point = Point(0, 0)
        set(value) {
            field = value
            invalidate()
        }
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.strokeWidth = Utils.dp2px(15f)
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPoint(point.x.toFloat(), point.y.toFloat(), paint)
    }
}