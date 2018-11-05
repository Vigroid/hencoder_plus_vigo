package com.tictalk.hencoder_8

import android.content.Context
import android.graphics.*
import android.graphics.Paint.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.tictalk.core.Utils

class ImageTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val textPaint = TextPaint()

    private var bitmap: Bitmap
    private val floatArray = floatArrayOf()

    val text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."


    init {
        textPaint.textSize = Utils.dp2px(12f)
        paint.textSize = Utils.dp2px(12f)
        bitmap = Utils.getAvatar(resources, Utils.dp2px(100f).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawBitmap(bitmap, width - Utils.dp2px(100f), 120f, paint)
        var curForwardIndex = 0
        var previous: Int
        var index = 0
        var yPos: Float


        while (curForwardIndex < text.length) {

            yPos = 50f + index * paint.fontSpacing
            previous = curForwardIndex

            when (yPos) {
                in 120f..(120 + Utils.dp2px(100f) + paint.fontSpacing) -> {
                    //当前测量此行最后一个字符的index
                    curForwardIndex = paint.breakText(
                        text, curForwardIndex, text.length, true,
                        width.toFloat() - Utils.dp2px(100f), floatArray
                    )
                }

                else -> {
                    //当前测量此行最后一个字符的index
                    curForwardIndex = paint.breakText(
                        text, curForwardIndex, text.length, true,
                        width.toFloat(), floatArray
                    )
                }
            }

            curForwardIndex += previous
            Log.i("vigo", "$previous/$curForwardIndex")

            //绘制
            canvas?.drawText(text, previous, curForwardIndex, 0f, yPos, paint)
            index++
        }


    }
}