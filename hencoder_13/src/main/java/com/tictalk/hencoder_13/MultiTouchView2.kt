package com.tictalk.hencoder_13

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.tictalk.core.Utils
import java.lang.Exception

class MultiTouchView2(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val image_length = Utils.dp2px(200f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap: Bitmap
    private var offsetX = 0f
    private var offsetY = 0f

    //down事件时坐标系的初始偏移
    private var initialOffsetX = 0f
    private var initialOffsetY = 0f
    //down事件时event x和y，便于计算move后的偏移量
    private var downX = 0f
    private var downY = 0f

    private var focusX = 0f
    private var focusY = 0f

    init {
        bitmap = Utils.getAvatar(context.resources, image_length.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val count = event?.pointerCount ?: 0
        var sumX = 0f
        var sumY = 0f
        for (i in 0 until count) {
            sumX += event?.getX(i) ?: 0f
            sumY += event?.getY(i) ?: 0f
        }
        focusX = sumX / count
        focusY = sumY / count

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = focusX
                downY = focusY
                initialOffsetX = offsetX
                initialOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_POINTER_DOWN-> {
                //new - old(when down) + original offset(offset when down)
                try {
                    offsetX = focusX - downX + initialOffsetX
                    offsetY = focusY - downY + initialOffsetY
                    invalidate()
                } catch (ex: Exception) {
                    Log.e("vigo", ex.toString())
                }
            }
        }
        return true
    }

}