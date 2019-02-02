package com.tictalk.hencoder12

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val IMAGE_WIDTH = Utils.dp2px(300f)

    var bitmap: Bitmap
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            drawBitmap(bitmap,0f,0f,paint)
        }
    }
}