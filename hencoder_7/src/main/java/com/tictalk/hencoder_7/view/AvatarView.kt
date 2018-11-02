package com.tictalk.hencoder_7.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tictalk.core.Utils
import com.tictalk.hencoder_7.R

class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val WIDTH: Float = Utils.dp2px(300f)
    val PADDING: Float = Utils.dp2px(50f)
    val EDGE: Float = Utils.dp2px(10f)

    var bitmap: Bitmap
    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    var saveArea: RectF = RectF()

    init {
        bitmap = getAvatar(WIDTH.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint)
        val saved: Int? = canvas?.saveLayer(saveArea, paint)
        canvas?.drawOval(PADDING + EDGE, PADDING + EDGE, PADDING + WIDTH - EDGE, PADDING + WIDTH - EDGE, paint)
        paint.xfermode = mode
        canvas?.drawBitmap(bitmap, PADDING, PADDING, paint)
        paint.xfermode = null
        canvas?.restoreToCount(saved!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        saveArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH)
    }

    fun getAvatar(width: Int): Bitmap {
        var options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }
}