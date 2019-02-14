package com.tictalk.hencoder12

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.tictalk.core.Utils

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private val IMAGE_WIDTH = Utils.dp2px(300f)
    private val OVERWHELMING_SCALE = 1.5f

    private val bitmap: Bitmap
    private val detector: GestureDetectorCompat
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX = 0f
    private var offsetY = 0f
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    //按短边填充满
    private var smallScale = 1f
    //按长边填充满
    private var bigScale = 1f
    private var isBig = false
    //放大缩小动画系数
    private var scaleFraction = 0f
        set(value) {
            invalidate()
            field = value
        }
    private val scaleAnimator by lazy {
        ObjectAnimator.ofFloat(this@ScalableImageView, "scaleFraction", 0f, 1f)
    }

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        detector = GestureDetectorCompat(context, this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width - bitmap.width).toFloat() / 2f
        originalOffsetY = (height - bitmap.height).toFloat() / 2f

        val viewAspect = width / height
        val bitmapAspect = bitmap.width / bitmap.height
        //图片比view宽
        if (bitmapAspect > viewAspect) {
            smallScale = width.toFloat() / bitmap.width.toFloat()
            bigScale = (height.toFloat() / bitmap.height.toFloat()) * OVERWHELMING_SCALE
        } else {
            bigScale = (width.toFloat() / bitmap.width.toFloat()) * OVERWHELMING_SCALE
            smallScale = height.toFloat() / bitmap.height.toFloat()
        }
        Log.i("vigo", "sizechange")
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            canvas.translate(offsetX, offsetY)
            val scale = (bigScale - smallScale) * scaleFraction + smallScale
            canvas.scale(scale, scale, width / 2f, height / 2f)
            drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false

    override fun onDown(e: MotionEvent?): Boolean = true

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean = false

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        if (isBig) {
            offsetX -= distanceX
            offsetX = Math.min(offsetX, (bitmap.width * bigScale - width)/2)
            offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width)/2)
            offsetY -= distanceY
            offsetY = Math.min(offsetY, (bitmap.height * bigScale - height)/2)
            offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height)/2)
            invalidate()
        }
        return false
    }

    override fun onLongPress(e: MotionEvent?) {}

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isBig = !isBig
        if (isBig) {
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean = false

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean = false
}