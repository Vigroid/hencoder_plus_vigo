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

class MultiTouchView1(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
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

    private var neoPointerId = -1

    init {
        bitmap = Utils.getAvatar(context.resources, image_length.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN->{
                downX = event.x
                downY = event.y
                initialOffsetX = offsetX
                initialOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE->{
                //new - old(when down) + original offset(offset when down)
                offsetX = event.x - downX + initialOffsetX
                offsetY = event.y - downY + initialOffsetY
                invalidate()
            }
        }
        return true
    }

}