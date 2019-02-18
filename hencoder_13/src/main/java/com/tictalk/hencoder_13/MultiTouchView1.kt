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

    private var activePointerId = -1

    init {
        bitmap = Utils.getAvatar(context.resources, image_length.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                activePointerId = event.findPointerIndex(0)
                downX = event.x
                downY = event.y
                initialOffsetX = offsetX
                initialOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                val activeIndex = event.findPointerIndex(activePointerId)
                //new - old(when down) + original offset(offset when down)
                try {
                    offsetX = event.getX(activeIndex) - downX + initialOffsetX
                    offsetY = event.getY(activeIndex) - downY + initialOffsetY
                    invalidate()
                }catch (ex:Exception){
                    Log.e("vigo",ex.toString())
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                activePointerId = event.getPointerId(event.actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                initialOffsetX = offsetX
                initialOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val upPointerId = event.getPointerId(event.actionIndex)
                //抬起的是控制移动的手指
                if (upPointerId == activePointerId) {
                    //是否是id最大的手指
                    val newIndex =
                        if (event.actionIndex == event.pointerCount - 1) event.pointerCount - 2 else event.pointerCount - 1
                    activePointerId = event.getPointerId(newIndex)
                    downX = event.getX(event.actionIndex)
                    downY = event.getY(event.actionIndex)
                    initialOffsetX = offsetX
                    initialOffsetY = offsetY
                }
            }
        }
        return true
    }

}