package com.samyak2403.tastytoast


import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class SuccessToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectF = RectF()
    private var valueAnimator: ValueAnimator? = null
    private var mAnimatedValue = 0f
    private lateinit var mPaint: Paint
    private var mWidth = 0f
    private var mEyeWidth = 0f
    private var mPadding = 0f
    private var endAngle = 0f
    private var isSmileLeft = false
    private var isSmileRight = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPaint()
        initRect()
        mWidth = measuredWidth.toFloat()
        mPadding = dip2px(10f)
        mEyeWidth = dip2px(3f)
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#5cb85c")
            strokeWidth = dip2px(2f)
        }
    }

    private fun initRect() {
        rectF.set(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(rectF, 180f, endAngle, false, mPaint)

        mPaint.style = Paint.Style.FILL
        if (isSmileLeft) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint)
        }
        if (isSmileRight) {
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint)
        }
    }

    private fun dip2px(dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

    fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 2000)
    }

    fun stopAnim() {
        valueAnimator?.let {
            clearAnimation()
            isSmileLeft = false
            isSmileRight = false
            mAnimatedValue = 0f
            it.end()
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long): ValueAnimator {
        valueAnimator = ValueAnimator.ofFloat(startF, endF).apply {
            duration = time
            interpolator = LinearInterpolator()
            addUpdateListener {
                mAnimatedValue = it.animatedValue as Float
                when {
                    mAnimatedValue < 0.5 -> {
                        isSmileLeft = false
                        isSmileRight = false
                        endAngle = -360 * mAnimatedValue
                    }
                    mAnimatedValue in 0.55..0.7 -> {
                        endAngle = -180f
                        isSmileLeft = true
                        isSmileRight = false
                    }
                    else -> {
                        endAngle = -180f
                        isSmileLeft = true
                        isSmileRight = true
                    }
                }
                postInvalidate()
            }
        }
        if (valueAnimator?.isRunning == false) {
            valueAnimator?.start()
        }
        return valueAnimator!!
    }
}
