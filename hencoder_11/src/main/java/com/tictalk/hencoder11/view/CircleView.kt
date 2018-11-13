package com.tictalk.hencoder11.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val padding = Utils.dp2px(30f)
    val radius = Utils.dp2px(80f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = (padding + radius).toInt() * 2
        var height = (padding + radius).toInt() * 2

        width = resolveSize(width, widthMeasureSpec)
        height = resolveSize(height, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.RED)
        canvas?.drawCircle(padding + radius, padding + radius, radius, paint)
    }
}