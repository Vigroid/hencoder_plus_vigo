package com.tictalk.hencoder_13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.tictalk.core.Utils

class MultiTouchView3(context: Context, attributeSet: AttributeSet):View(context, attributeSet){
    private val paint= Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(4f)
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN->{
                path.moveTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE->{
                path.lineTo(event.x, event.y)
                invalidate()
            }
            MotionEvent.ACTION_UP->{
                path.reset()
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }
}