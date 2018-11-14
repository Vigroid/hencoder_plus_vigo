package com.tictalk.hencoder11.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val childrenBounds = arrayListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var maxWidthUsed = 0
        var heightUsed = 0
        var lineWidthUsed = 0
        var lineMaxHeight = 0
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specSize = MeasureSpec.getSize(widthMeasureSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (specMode != MeasureSpec.UNSPECIFIED && (child.measuredWidth + lineWidthUsed > specSize)) {
                lineWidthUsed = 0
                heightUsed += lineMaxHeight
                Log.i("vig_debug","$heightUsed, $lineMaxHeight")
                lineMaxHeight = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            if (childrenBounds.size <= i) {
                childrenBounds.add(Rect())
            }
            childrenBounds[i].set(
                lineWidthUsed,
                heightUsed,
                lineWidthUsed + child.measuredWidth,
                heightUsed + child.measuredHeight
            )
            lineWidthUsed += child.measuredWidth

            lineMaxHeight = Math.max(lineMaxHeight, child.measuredHeight)
            maxWidthUsed = Math.max(maxWidthUsed, lineWidthUsed)
        }

        setMeasuredDimension(maxWidthUsed, heightUsed+ lineMaxHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childRect = childrenBounds[i]
            Log.i("vigo_layout", "$childRect")
            child.layout(childRect.left, childRect.top, childRect.right, childRect.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}