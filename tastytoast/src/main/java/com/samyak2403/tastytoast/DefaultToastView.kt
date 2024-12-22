package com.samyak2403.tastytoast



import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class DefaultToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var valueAnimator: ValueAnimator? = null
    private var mAnimatedValue = 0f
    private lateinit var mPaint: Paint
    private lateinit var mSpikePaint: Paint
    private var mWidth = 0f
    private var mPadding = 0f
    private var mSpikeLength = 0f

    init {
        initPaint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mPadding = dip2px(5f)
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#222222")
            strokeWidth = dip2px(2f)
        }

        mSpikePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#222222")
            strokeWidth = dip2px(4f)
        }

        mSpikeLength = dip2px(4f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 4, mPaint)

        for (i in 0 until 360 step 40) {
            val angle = (mAnimatedValue * 40 + i).toInt()
            val initialX = (mWidth / 4 * Math.cos(Math.toRadians(angle.toDouble()))).toFloat()
            val initialY = (mWidth / 4 * Math.sin(Math.toRadians(angle.toDouble()))).toFloat()
            val finalX = ((mWidth / 4 + mSpikeLength) * Math.cos(Math.toRadians(angle.toDouble()))).toFloat()
            val finalY = ((mWidth / 4 + mSpikeLength) * Math.sin(Math.toRadians(angle.toDouble()))).toFloat()

            canvas.drawLine(
                mWidth / 2 - initialX,
                mWidth / 2 - initialY,
                mWidth / 2 - finalX,
                mWidth / 2 - finalY,
                mSpikePaint
            )
        }
        canvas.restore()
    }

    fun startAnim() {
        stopAnim()
        startViewAnim(0f, 1f, 2000)
    }

    fun stopAnim() {
        valueAnimator?.let {
            clearAnimation()
            it.end()
            postInvalidate()
        }
    }

    private fun startViewAnim(startF: Float, endF: Float, time: Long): ValueAnimator {
        valueAnimator = ValueAnimator.ofFloat(startF, endF).apply {
            duration = time
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                mAnimatedValue = it.animatedValue as Float
                postInvalidate()
            }
            if (!isRunning) start()
        }
        return valueAnimator as ValueAnimator
    }

    private fun dip2px(dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale
    }
}
