package com.tictalk.hencoder11

import android.content.Context
import android.util.AttributeSet
import android.view.View

class HundredView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(100,100)
    }
}