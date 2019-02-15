package com.tictalk.hencoder12

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import com.tictalk.core.Utils

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs){

    private val IMAGE_WIDTH = Utils.dp2px(300f)
    private val OVERWHELMING_SCALE = 1.5f

    private val bitmap: Bitmap
    private val detector: GestureDetectorCompat
    private val overScroller: OverScroller
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
    private val scalableGestureDetector = ScalableGestureDetector()
    private val scalableRunnable = ScalableRunnable()
    private val scaleDetector:ScaleGestureDetector
    private val scaleListener = ScalableListener()

    //放大缩小动画系数
    private var currentScale = 0f
        set(value) {
            invalidate()
            field = value
        }
    private val scaleAnimator by lazy {
        val result = ObjectAnimator.ofFloat(this@ScalableImageView, "currentScale", 0f, 1f)
        result.setFloatValues(smallScale, bigScale)
        return@lazy result
    }

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        detector = GestureDetectorCompat(context, scalableGestureDetector)
        overScroller = OverScroller(context)
        scaleDetector =  ScaleGestureDetector(context, scaleListener)
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
        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            val scaleFraction = (currentScale - smallScale)/ (bigScale - smallScale)
            canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
            canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
            drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = scaleDetector.onTouchEvent(event)
        if (!scaleDetector.isInProgress){
            result = detector.onTouchEvent(event)
        }
        return result
    }

    private fun constraintOffset() {
        offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)

        offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    inner class ScalableRunnable : Runnable {
        override fun run() {
            if (overScroller.computeScrollOffset()) {
                offsetX = overScroller.currX.toFloat()
                offsetY = overScroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }

    inner class ScalableGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onShowPress(e: MotionEvent?) {}

        override fun onSingleTapUp(e: MotionEvent?): Boolean = false

        override fun onDown(e: MotionEvent?): Boolean = true

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (isBig) {
                overScroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt()
                )
                postOnAnimation(scalableRunnable)
            }
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (isBig) {
                offsetX -= distanceX
                offsetY -= distanceY
                constraintOffset()
                invalidate()
            }
            return false
        }

        override fun onLongPress(e: MotionEvent?) {}

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            isBig = !isBig
            if (isBig) {
                e?.x?.let {
                    offsetX = (it - width / 2f) - (it - width / 2f) * bigScale / smallScale
                    offsetY = (e.y - height / 2f) - (e.y - height / 2f) * bigScale / smallScale
                    constraintOffset()
                }
                scaleAnimator.start()
            } else {
                scaleAnimator.reverse()
            }
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean = false

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean = false
    }

    inner class ScalableListener: ScaleGestureDetector.OnScaleGestureListener{
        var initialScale = 0f
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean{
            initialScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {}

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            detector?.scaleFactor?.let {
                currentScale = it * initialScale
                invalidate()
            }
            return false
        }

    }
}