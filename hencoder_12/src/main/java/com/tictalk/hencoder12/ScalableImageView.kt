package com.tictalk.hencoder12

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.tictalk.core.Utils

class ScalableImageView(context: Context, attrs: AttributeSet) : View(context, attrs), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private val IMAGE_WIDTH = Utils.dp2px(300f)

    private val bitmap: Bitmap
    private val detector: GestureDetectorCompat
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var offsetX = 0f
    private var offsetY = 0f

    //按短边填充满
    private var smallScale = 1f
    //按长边填充满
    private var bigScale = 1f

    private var isBig = false

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        detector = GestureDetectorCompat(context, this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (width - bitmap.width).toFloat() / 2f
        offsetY = (height - bitmap.height).toFloat() / 2f

        val viewAspect = width / height
        val bitmapAspect = bitmap.width / bitmap.height
        //图片比view宽
        if (bitmapAspect > viewAspect) {
            smallScale = width.toFloat() / bitmap.width.toFloat()
            bigScale = height.toFloat() / bitmap.height.toFloat()
        } else {
            bigScale = width.toFloat() / bitmap.width.toFloat()
            smallScale = height.toFloat() / bitmap.height.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            val scale = if(isBig) bigScale else smallScale
            canvas.scale(scale, scale, width / 2f, height / 2f)
            drawBitmap(bitmap, offsetX, offsetY, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean = false

    override fun onDown(e: MotionEvent?): Boolean = true

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean = false

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean = false

    override fun onLongPress(e: MotionEvent?) {}

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isBig = !isBig
        invalidate()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean = false

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean = false
}