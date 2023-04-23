package com.module.captcha

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatSeekBar

class TextSeekbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = R.style.TextSeekbarStyle
) : AppCompatSeekBar(context, attrs, defStyleAttr) {

    private var textPaint = Paint()

    private var downTime = 0L

    init {
        splitTrack = false
        thumbOffset = 0
        textPaint.textAlign = Paint.Align.CENTER
        val textSize = Utils.dp2px(context, 14f)
        textPaint.textSize = textSize.toFloat()
        textPaint.isAntiAlias = true
        textPaint.color = Color.parseColor("#545454")
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val fontMetrics = textPaint.fontMetrics
        val top = fontMetrics.top
        val bottom = fontMetrics.bottom
        val baseLineY = (height / 2 - top / 2 - bottom / 2).toInt()
        canvas.drawText(
            "Slide the slider to the right to complete the puzzle",
            (width / 2).toFloat(),
            baseLineY.toFloat(),
            textPaint
        )
    }
}