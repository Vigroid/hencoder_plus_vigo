package com.tictalk.hencoder_10

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.tictalk.core.Utils

class MaterialEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    val TEXT_SIZE = Utils.dp2px(12f)
    val TEXT_MARGIN = Utils.dp2px(8f)
    private val TEXT_VERTICAL_OFFSET = Utils.dp2px(22f)
    private val TEXT_HORI_OFFSET = Utils.dp2px(5f)
    private val TEXT_ANIM_OFFSET = Utils.dp2px(16f)

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var floatingLabelShown = false
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    var useFloatingLabel = true
    set(value) {
        if (field!=value){
            field = value
            if (field){
                setPadding(paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
            } else{
                setPadding(paddingLeft, (paddingTop - TEXT_SIZE - TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
            }
        }
    }

    val floatingLabelAnimator = ObjectAnimator.ofFloat(this@MaterialEditText, "floatingLabelFraction", 1f, 0f)

    init {
        paint.textSize = TEXT_SIZE
        if (useFloatingLabel) {
            setPadding(paddingLeft, (paddingTop + TEXT_SIZE + TEXT_MARGIN).toInt(), paddingRight, paddingBottom)
        }

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (floatingLabelShown && s.isNullOrEmpty()) {
                    floatingLabelAnimator.start()
                    floatingLabelShown = false
                } else if (!floatingLabelShown && !s.isNullOrEmpty()) {
                    floatingLabelAnimator.reverse()
                    floatingLabelShown = true
                }
            }
        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.alpha = (floatingLabelFraction * 0xff).toInt()
        val extraOffset = TEXT_ANIM_OFFSET * (1 - floatingLabelFraction)
        canvas?.drawText(hint.toString(), TEXT_HORI_OFFSET, TEXT_VERTICAL_OFFSET +extraOffset, paint)
    }
}