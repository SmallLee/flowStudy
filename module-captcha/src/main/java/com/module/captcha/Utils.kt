package com.module.captcha

import android.content.Context

object Utils {

    @JvmStatic
    fun dp2px(ctx: Context, dip: Float): Int {
        val density = ctx.resources.displayMetrics.density
        return (dip * density).toInt()
    }
}