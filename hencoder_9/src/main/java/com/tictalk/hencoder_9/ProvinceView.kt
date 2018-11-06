package com.tictalk.hencoder_9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class ProvinceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var province = "北京市"
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.textSize = Utils.dp2px(100f)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawText(province, width / 2f, height / 2f, paint)
    }
}