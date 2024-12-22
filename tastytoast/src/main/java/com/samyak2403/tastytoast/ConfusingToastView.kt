package com.samyak2403.tastytoast



import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class ConfusingToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var eye: Bitmap? = null
    private var valueAnimator: ValueAnimator? = null
    private var angle = 0f
    private var mPaint = Paint()
    private var mWidth = 0f
    private var mHeight = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
        initPaint()
        initPath()
    }

    private fun initPaint() {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.color = Color.parseColor("#FE9D4D")
    }

    private fun initPath() {
        val mPath = Path()
        val rectF = RectF(
            mWidth / 2f - dip2px(1.5f),
            mHeight / 2f - dip2px(1.5f),
            mWidth / 2f + dip2px(1.5f),
            mHeight / 2f + dip2px(1.5f)
        )
        mPath.addArc(rectF, 180f, 180f)
        rectF.set(
            rectF.left - dip2px(3f),
            rectF.top - dip2px(1.5f),
            rectF.right,
            rectF.bottom + dip2px(1.5f)
        )
        mPath.addArc(rectF, 0f, 180f)
        rectF.set(
            rectF.left,
            rectF.top - dip2px(1.5f),
            rectF.right + dip2px(3f),
            rectF.bottom + dip2px(1.5f)
        )
        mPath.addArc(rectF, 180f, 180f)
        rectF.set(
            rectF.left - dip2px(3f),
            rectF.top - dip2px(1.5f),
            rectF.right,
            rectF.bottom + dip2px(1.5f)
        )
        mPath.addArc(rectF, 0f, 180f)

        eye = Bitmap.createBitmap(mWidth.toInt(), mHeight.toInt(), Bitmap.Config.ARGB_8888).also {
            val canvas = Canvas(it)
            mPaint.strokeWidth = dip2px(1.7f)
            canvas.drawPath(mPath, mPaint)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.rotate(angle, mWidth / 4f, mHeight * 2f / 5f)
        eye?.let {
            canvas.drawBitmap(it, mWidth / 4f - it.width / 2f, mHeight * 2f / 5f - it.height / 2f, mPaint)
        }
        canvas.restore()

        canvas.save()
        canvas.rotate(angle, mWidth * 3f / 4f, mHeight * 2f / 5f)
        eye?.let {
            canvas.drawBitmap(it, mWidth * 3f / 4f - it.width / 2f, mHeight * 2f / 5f - it.height / 2f, mPaint)
        }
        canvas.restore()

        mPaint.strokeWidth = dip2px(2f)
        canvas.drawLine(mWidth / 4f, mHeight * 3f / 4f, mWidth * 3f / 4f, mHeight * 3f / 4f, mPaint)
    }

    private fun dip2px(dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale
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
                angle += 4
                postInvalidate()
            }
            if (!isRunning) start()
        }
        return valueAnimator!!
    }
}
