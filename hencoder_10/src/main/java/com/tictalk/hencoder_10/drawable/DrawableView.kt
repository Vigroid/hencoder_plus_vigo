package com.tictalk.hencoder_10.drawable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View

class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var drawable = MeshDrawable()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawable.setBounds(0,0,width,height)
        drawable.draw(canvas)
    }
}