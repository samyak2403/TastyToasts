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

class ErrorToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rectF = RectF()
    private var leftEyeRectF = RectF()
    private var rightEyeRectF = RectF()
    private var valueAnimator: ValueAnimator? = null
    private var mAnimatedValue = 0f
    private lateinit var mPaint: Paint
    private var mWidth = 0f
    private var mEyeWidth = 0f
    private var mPadding = 0f
    private var endAngle = 0f
    private var isJustVisible = false
    private var isSad = false

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
            color = Color.parseColor("#d9534f")
            strokeWidth = dip2px(2f)
        }
    }

    private fun initRect() {
        rectF = RectF(
            mPadding / 2,
            mWidth / 2,
            mWidth - mPadding / 2,
            3 * mWidth / 2
        )
        leftEyeRectF = RectF(
            mPadding + mEyeWidth - mEyeWidth,
            mWidth / 3 - mEyeWidth,
            mPadding + mEyeWidth + mEyeWidth,
            mWidth / 3 + mEyeWidth
        )
        rightEyeRectF = RectF(
            mWidth - mPadding - 5 * mEyeWidth / 2,
            mWidth / 3 - mEyeWidth,
            mWidth - mPadding - mEyeWidth / 2,
            mWidth / 3 + mEyeWidth
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(rectF, 210f, endAngle, false, mPaint)

        mPaint.style = Paint.Style.FILL
        if (isJustVisible) {
            canvas.drawCircle(
                mPadding + mEyeWidth + mEyeWidth / 2,
                mWidth / 3,
                mEyeWidth,
                mPaint
            )
            canvas.drawCircle(
                mWidth - mPadding - mEyeWidth - mEyeWidth / 2,
                mWidth / 3,
                mEyeWidth,
                mPaint
            )
        }
        if (isSad) {
            canvas.drawArc(leftEyeRectF, 160f, -220f, false, mPaint)
            canvas.drawArc(rightEyeRectF, 20f, 220f, false, mPaint)
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
        valueAnimator?.apply {
            clearAnimation()
            isSad = false
            endAngle = 0f
            isJustVisible = false
            mAnimatedValue = 0f
            end()
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
                        isSad = false
                        isJustVisible = false
                        endAngle = 240 * mAnimatedValue
                        isJustVisible = true
                    }
                    mAnimatedValue > 0.55 && mAnimatedValue < 0.7 -> {
                        endAngle = 120f
                        isSad = false
                        isJustVisible = true
                    }
                    else -> {
                        endAngle = 120f
                        isSad = true
                        isJustVisible = false
                    }
                }
                postInvalidate()
            }
            if (!isRunning) start()
        }
        return valueAnimator!!
    }
}
