package com.tictalk.core

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

object Utils {

    @JvmStatic
    val provinces = arrayListOf("北京市","天津市","上海市","江苏省","浙江省","山东省")

    @JvmStatic
    fun dp2px(dp:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }

    @JvmStatic
    fun getAvatar(resources: Resources, width: Int): Bitmap {
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar, options)
    }

    @JvmStatic
    fun getZFromCamera() = -6f* Resources.getSystem().displayMetrics.density
}