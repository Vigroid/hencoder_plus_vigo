package com.tictalk.hencoder_13

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import com.tictalk.core.Utils

class MultiTouchView3(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paths = SparseArray<Path>()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(4f)
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN ,MotionEvent.ACTION_POINTER_DOWN-> {
                val path = Path()
                path.moveTo(event.getX(event.actionIndex), event.getY(event.actionIndex))
                paths.append(event.getPointerId(event.actionIndex), path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val path = paths.get(event.getPointerId(i))
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val pointId = event.getPointerId(event.actionIndex)
                paths.remove(pointId)
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        for (i in 0 until paths.size()) {
            canvas?.drawPath(paths.valueAt(i), paint)
        }
    }
}