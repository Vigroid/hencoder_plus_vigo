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

        val animator = ObjectAnimator.ofObject(view,"province",ProvinceEvaluator(),"山东省")
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

class ProvinceEvaluator:TypeEvaluator<String>{
    override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
        val startIndex = Utils.provinces.indexOf(startValue)
        val endIndex = Utils.provinces.indexOf(endValue)
        val index = startIndex + (endIndex - startIndex) * fraction

        return Utils.provinces[index.toInt()]
    }

}
