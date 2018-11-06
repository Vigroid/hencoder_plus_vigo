package com.tictalk.hencoder_10.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import com.tictalk.core.Utils

class MeshDrawable : Drawable() {
    val INTERVAL = Utils.dp2px(8f).toInt()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = Color.RED
        paint.strokeWidth = Utils.dp2px(2f)
    }

    override fun draw(canvas: Canvas?) {
        for (h in 0..bounds.right step INTERVAL){
            for (v in 0..bounds.bottom step INTERVAL){
                canvas?.drawLine(bounds.left.toFloat(),v.toFloat(), bounds.right.toFloat(), v.toFloat(), paint)
                canvas?.drawLine(h.toFloat(),bounds.top.toFloat(), h.toFloat(), bounds.bottom.toFloat(), paint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return paint.alpha
    }

    override fun getOpacity(): Int {
        return when (paint.alpha) {
            0 -> PixelFormat.TRANSPARENT
            0xff -> PixelFormat.OPAQUE
            else -> {
                PixelFormat.TRANSLUCENT
            }
        }
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getColorFilter(): ColorFilter {
        return paint.colorFilter
    }
}