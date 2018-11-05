package com.tictalk.hencoder_9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tictalk.core.Utils

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var radius = Utils.dp2px(50f)
        set(value) {
            Log.i("vig",value.toString())
            field = value
            invalidate()
        }

    init {
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(width / 2f, height / 2f, radius, paint)
    }
}