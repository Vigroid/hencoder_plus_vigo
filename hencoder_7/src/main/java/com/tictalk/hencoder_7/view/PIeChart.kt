package com.tictalk.hencoder_7.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class PIeChart(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val RADIUS: Float = Utils.dp2px(150f)
    val LENGTH = Utils.dp2px(20f)
    val PULL_OUT_INDEX = 2

    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bounds: RectF = RectF()
    val angle: FloatArray = floatArrayOf(60f, 100f, 120f, 80f)
    val colors: IntArray = intArrayOf(Color.parseColor("#2979FF"),
            Color.parseColor("#C2185B"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF8F00"))

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bounds.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var curAngle = 0f
        for (i in 0..3) {
            paint.color = colors[i]
            val curRadiant = Math.toRadians((curAngle + angle[i]/2).toDouble())
            canvas?.save()
            //change the canvas position instead of changing the bounds
            if (i == PULL_OUT_INDEX)
                canvas?.translate((Math.cos(curRadiant) * LENGTH).toFloat(), (Math.sin(curRadiant) * LENGTH).toFloat())

            canvas?.drawArc(bounds, curAngle, angle[i], true, paint)
            curAngle += angle[i]

            canvas?.restore()
        }
    }
}