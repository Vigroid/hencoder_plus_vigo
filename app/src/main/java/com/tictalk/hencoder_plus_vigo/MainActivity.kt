package com.tictalk.hencoder_plus_vigo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomFlipAnimator= ObjectAnimator.ofFloat(view, "bottomFlip", 45f)
        bottomFlipAnimator.duration = 1500

        val rotationFlipAnimator= ObjectAnimator.ofFloat(view, "flipRotation", 270f)
        rotationFlipAnimator.duration = 1500

        val topFlipAnimator= ObjectAnimator.ofFloat(view, "topFlip", -45f)
        topFlipAnimator.duration = 1500

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator, rotationFlipAnimator, topFlipAnimator)
        animatorSet.startDelay = 1000
        animatorSet.start()
    }
}
