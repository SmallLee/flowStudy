package com.module.captcha

import android.animation.Animator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.module.captcha.Utils.dp2px

class Captcha @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var verifyView
            : PictureVerifyView? = null
    private var seekbar
            : TextSeekbar? = null
    private var accessSuccess: View? = null
    private var accessFailed
            : View? = null
    private var accessText: TextView? = null
    private var accessFailedText
            : TextView? = null
    private var refreshView
            : ImageView? = null

    private var drawableId = -1
    private var progressDrawableId = 0
    private var thumbDrawableId = 0
    private var mMode = 0
    var maxFailedCount = 0
    private var failCount = 0
    private var blockSize = 0
    private var isResponse = false
    private var isDown = false
    private var mListener: CaptchaListener? = null

    @IntDef(value = [MODE_BAR, MODE_NONBAR])
    annotation class Mode
    interface CaptchaListener {

        fun onAccess(time: Long): String?

        fun onFailed(failCount: Int): String?

        fun onMaxFailed(): String?
    }


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Captcha)
        drawableId = typedArray.getResourceId(R.styleable.Captcha_src, R.drawable.cat)
        progressDrawableId =
            typedArray.getResourceId(R.styleable.Captcha_progressDrawable, R.drawable.po_seekbar)
        thumbDrawableId =
            typedArray.getResourceId(R.styleable.Captcha_thumbDrawable, R.drawable.thumb)
        mMode = typedArray.getInteger(R.styleable.Captcha_mode, MODE_BAR)
        maxFailedCount = typedArray.getInteger(R.styleable.Captcha_max_fail_count, 3)
        blockSize = typedArray.getDimensionPixelSize(
            R.styleable.Captcha_blockSize,
            dp2px(getContext(), 50f)
        )
        typedArray.recycle()
        init()
    }

    private fun init() {
        val parentView = LayoutInflater.from(context).inflate(R.layout.container, this, true)
        verifyView = parentView.findViewById<View>(R.id.verifyView) as PictureVerifyView
        seekbar = parentView.findViewById<View>(R.id.seekbar) as TextSeekbar
        accessSuccess = parentView.findViewById(R.id.accessRight)
        accessFailed = parentView.findViewById(R.id.accessFailed)
        accessText = parentView.findViewById<View>(R.id.accessText) as TextView
        accessFailedText = parentView.findViewById<View>(R.id.accessFailedText) as TextView
        refreshView = parentView.findViewById<View>(R.id.refresh) as ImageView
        mode = mMode
        if (drawableId != -1) {
            verifyView!!.setImageResource(drawableId)
        }
        setBlockSize(blockSize)
        verifyView?.verifyCallback(object : PictureVerifyView.Callback {
            override fun onSuccess(time: Long) {
                if (mListener != null) {
                    val s = mListener?.onAccess(time)
                    if (s != null) {
                        accessText?.text = s
                    } else {
                        accessText?.text =
                            String.format(resources.getString(R.string.verify_access), time)
                    }
                }
                accessSuccess?.visibility = VISIBLE
                accessFailed?.visibility = GONE
            }

            override fun onFailed() {
                seekbar!!.isEnabled = false
                verifyView!!.setTouchEnable(false)
                failCount = if (failCount > maxFailedCount) maxFailedCount else failCount + 1
                accessFailed?.visibility = VISIBLE
                accessSuccess?.visibility = GONE
                if (mListener != null) {
                    if (failCount == maxFailedCount) {
                        val s = mListener!!.onMaxFailed()
                        if (s != null) {
                            accessFailedText?.text = s
                        } else {
                            accessFailedText?.text = String.format(
                                resources.getString(R.string.verify_failed),
                                maxFailedCount - failCount
                            )
                        }
                    } else {
                        val s = mListener?.onFailed(failCount)
                        if (s != null) {
                            accessFailedText?.text = s
                        } else {
                            accessFailedText?.text = String.format(
                                resources.getString(R.string.verify_failed),
                                maxFailedCount - failCount
                            )
                        }
                    }
                }
            }
        })
        setSeekBarStyle(progressDrawableId, thumbDrawableId)
        seekbar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (isDown) {
                    isDown = false
                    if (progress > 10) {
                        isResponse = false
                    } else {
                        isResponse = true
                        accessFailed?.visibility = GONE
                        verifyView!!.down(0)
                    }
                }
                if (isResponse) {
                    verifyView!!.move(progress)
                } else {
                    seekBar.progress = 0
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isDown = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (isResponse) {
                    verifyView!!.loose()
                }
            }
        })
        refreshView!!.setOnClickListener { v -> startRefresh(v) }
    }

    private fun startRefresh(v: View) {
        v.animate().rotationBy(360f).setDuration(500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    reset(false)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
    }

    fun setCaptchaListener(listener: CaptchaListener?) {
        mListener = listener
    }

    fun setCaptchaStrategy(strategy: CaptchaStrategy?) {
        if (strategy != null) {
            verifyView?.setCaptchaStrategy(strategy)
        }
    }

    fun setSeekBarStyle(@DrawableRes progressDrawable: Int, @DrawableRes thumbDrawable: Int) {
        seekbar?.progressDrawable =
            ResourcesCompat.getDrawable(resources, progressDrawable, context.theme)
        seekbar?.thumb = ResourcesCompat.getDrawable(resources, thumbDrawable, context.theme)
        seekbar?.thumbOffset = 0
    }

    private fun setBlockSize(blockSize: Int) {
        verifyView?.setBlockSize(blockSize)
    }

    var mode: Int
        get() = mMode
        set(mode) {
            mMode = mode
            verifyView!!.setMode(mode)
            if (mMode == MODE_NONBAR) {
                seekbar?.visibility = GONE
                verifyView?.setTouchEnable(true)
            } else {
                seekbar?.visibility = VISIBLE
                seekbar?.isEnabled = true
            }
            hideText()
        }

    fun setBitmap(drawableId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, drawableId)
        setBitmap(bitmap)
    }

    fun setBitmap(bitmap: Bitmap?) {
        verifyView!!.setImageBitmap(bitmap)
        reset(false)
    }

    fun setBitmap(url: String?) {
        Glide.with(context).asBitmap().load(url).into(object : CustomTarget<Bitmap?>() {

            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                setBitmap(resource)
            }
        })
    }

    fun reset(clearFailed: Boolean) {
        hideText()
        verifyView!!.reset()
        if (clearFailed) {
            failCount = 0
        }
        if (mMode == MODE_BAR) {
            seekbar?.isEnabled = true
            seekbar?.progress = 0
        } else {
            verifyView!!.setTouchEnable(true)
        }
    }

    private fun hideText() {
        accessFailed?.visibility = GONE
        accessSuccess?.visibility = GONE
    }

    companion object {

        const val MODE_BAR = 1

        const val MODE_NONBAR = 2
    }
}