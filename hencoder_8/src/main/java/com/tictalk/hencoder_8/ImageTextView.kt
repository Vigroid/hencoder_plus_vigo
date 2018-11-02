package com.tictalk.henplus_8.view

import android.content.Context
import android.graphics.*
import android.graphics.Paint.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils

class ImageTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint()
    private val radius = Utils.dp2px(100f)
    private val rect = Rect()
    private var bitmap: Bitmap
    private val floatArray = floatArrayOf()

    init {
        textPaint.textSize = Utils.dp2px(12f)
        paint.textSize = Utils.dp2px(12f)
        bitmap = Utils.getAvatar(resources, Utils.dp2px(100f).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val text =
            "Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃 Android入门到放弃"
        canvas?.drawBitmap(bitmap, width - Utils.dp2px(100f), 100f, paint)
        var oldIndex: Int
        var index = paint.breakText(text, true, width.toFloat(), floatArray)
        canvas?.drawText(text, 0, index, 0f, 50f, paint)
        oldIndex = index
        index = paint.breakText(text, index, text.length, true, width.toFloat(), floatArray)
        canvas?.drawText(text, oldIndex, oldIndex + index, 0f, 50f + paint.fontSpacing, paint)
        oldIndex = index
        index = paint.breakText(text, index, text.length, true, width - Utils.dp2px(100f), floatArray)
        canvas?.drawText(text, oldIndex, oldIndex + index, 0f, 50 + paint.fontSpacing * 2, paint)
    }


}