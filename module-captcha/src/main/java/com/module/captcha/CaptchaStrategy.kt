package com.module.captcha

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

abstract class CaptchaStrategy(protected var context: Context) {

    abstract fun getBlockShape(blockSize: Int): Path?

    abstract fun getBlockPositionInfo(width: Int, height: Int, blockSize: Int): PositionInfo?

    open fun getPositionInfoForSwipeBlock(width: Int, height: Int, blockSize: Int): PositionInfo? {
        return getBlockPositionInfo(width, height, blockSize)
    }

    abstract val blockShadowPaint: Paint?

    abstract val blockBitmapPaint: Paint?

    open fun decoreateSwipeBlockBitmap(canvas: Canvas?, shape: Path?) {}
}