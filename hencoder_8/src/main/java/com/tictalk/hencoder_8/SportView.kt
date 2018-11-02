package com.tictalk.henplus_8.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.*
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val radius = Utils.dp2px(100f)
    private val rect = Rect()
    private val fontMetrics: FontMetrics = FontMetrics()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.style = Style.STROKE
        paint.color = Color.parseColor("#66ccff")
        paint.strokeWidth = Utils.dp2px(10f)
        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radius, paint)

        paint.strokeCap = Cap.ROUND
        paint.color = Color.parseColor("#00FF00")
        canvas?.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            0f,
            90f,
            false,
            paint
        )

        paint.textSize = Utils.dp2px(50f)
        paint.style = Style.FILL
        paint.textAlign = Align.CENTER
        paint.getTextBounds("abab", 0, "abab".length, rect)
        val offset = (rect.top + rect.bottom)/2

        canvas?.drawText("abab", width.toFloat() / 2, height.toFloat() / 2 - offset, paint)
    }
}