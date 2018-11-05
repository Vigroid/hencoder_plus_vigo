package com.tictalk.hencoder_plus_vigo

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tictalk.core.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val targetPoint = Point(Utils.dp2px(300f).toInt(),Utils.dp2px(200f).toInt())
        val animator = ObjectAnimator.ofObject(view, "point",PointEvaluator(), targetPoint)
        animator.startDelay = 1000
        animator.duration = 1500

        animator.start()
    }
}

class PointEvaluator:TypeEvaluator<Point> {
    override fun evaluate(fraction: Float, startValue: Point?, endValue: Point?): Point {

        var x = 0f
        var y = 0f
        if (startValue != null && endValue !=null) {
            x = startValue.x + (endValue.x- startValue.x)* fraction
            y = startValue.y + (endValue.y- startValue.y)* fraction
        }
        return Point(x.toInt(), y.toInt())
    }
}
