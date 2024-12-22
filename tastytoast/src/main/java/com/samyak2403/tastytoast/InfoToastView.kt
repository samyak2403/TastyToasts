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

class InfoToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val rectF = RectF()
    private var valueAnimator: ValueAnimator? = null
    private var mAnimatedValue = 0f
    private val mPaint = Paint()
    private var mWidth = 0f
    private var mEyeWidth = 0f
    private var mPadding = 0f
    private var endPoint = 0f
    private var isEyeLeft = false
    private var isEyeRight = false
    private var isEyeMiddle = false

    init {
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mPadding = dip2px(10f)
        mEyeWidth = dip2px(3f)
        endPoint = mPadding
        initRect()
    }

    private fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.parseColor("#337ab7")
        mPaint.strokeWidth = dip2px(2f)
    }

    private fun initRect() {
        rectF.set(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.STROKE
        canvas.drawLine(mPadding, mWidth - 3 * mPadding / 2, endPoint, mWidth - 3 * mPadding / 2, mPaint)
        mPaint.style = Paint.Style.FILL

        when {
            isEyeLeft -> {
                canvas.drawCircle(mPadding + mEyeWidth, mWidth / 3, mEyeWidth, mPaint)
                canvas.drawCircle(mWidth - mPadding - 2 * mEyeWidth, mWidth / 3, mEyeWidth, mPaint)
            }
            isEyeMiddle -> {
                canvas.drawCircle(mPadding + (3 * mEyeWidth / 2), mWidth / 3, mEyeWidth, mPaint)
                canvas.drawCircle(mWidth - mPadding - (5 * mEyeWidth / 2), mWidth / 3, mEyeWidth, mPaint)
            }
            isEyeRight -> {
                canvas.drawCircle(mPadding + 2 * mEyeWidth, mWidth / 3, mEyeWidth, mPaint)
                canvas.drawCircle(mWidth - mPadding - mEyeWidth, mWidth / 3, mEyeWidth, mPaint)
            }
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
            isEyeLeft = false
            isEyeMiddle = false
            isEyeRight = false
            endPoint = mPadding
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

                endPoint = when {
                    mAnimatedValue < 0.90 -> ((2 * mWidth - 4 * mPadding) * (mAnimatedValue / 2)) + mPadding
                    else -> mWidth - 5 * mPadding / 4
                }

                when {
                    mAnimatedValue < 0.16 -> {
                        isEyeRight = true
                        isEyeLeft = false
                    }
                    mAnimatedValue < 0.32 -> {
                        isEyeRight = false
                        isEyeLeft = true
                    }
                    mAnimatedValue < 0.48 -> {
                        isEyeRight = true
                        isEyeLeft = false
                    }
                    mAnimatedValue < 0.64 -> {
                        isEyeRight = false
                        isEyeLeft = true
                    }
                    mAnimatedValue < 0.80 -> {
                        isEyeRight = true
                        isEyeLeft = false
                    }
                    mAnimatedValue < 0.96 -> {
                        isEyeRight = false
                        isEyeLeft = true
                    }
                    else -> {
                        isEyeLeft = false
                        isEyeMiddle = true
                        isEyeRight = false
                    }
                }

                postInvalidate()
            }
        }
        valueAnimator?.start()
        return valueAnimator!!
    }
}
