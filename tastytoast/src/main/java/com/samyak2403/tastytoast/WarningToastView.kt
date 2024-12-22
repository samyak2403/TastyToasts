package com.samyak2403.tastytoast



import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View

class WarningToastView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rectFOne = RectF()
    private var rectFTwo = RectF()
    private var rectFThree = RectF()
    private lateinit var mPaint: Paint
    private var mWidth = 0f
    private var mHeight = 0f
    private var mStrokeWidth = 0f
    private var mPadding = 0f
    private var mPaddingBottom = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initPaint()
        initRect()
        mHeight = measuredHeight.toFloat()
        mWidth = measuredWidth.toFloat()
        mPadding = convertDpToPixel(2f)
        mPaddingBottom = mPadding * 2
        mStrokeWidth = convertDpToPixel(2f)
    }

    private fun initPaint() {
        mPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = Color.parseColor("#f0ad4e")
            strokeWidth = mStrokeWidth
        }
    }

    private fun initRect() {
        rectFOne = RectF(mPadding, 0f, mWidth - mPadding, mWidth - mPaddingBottom)
        rectFTwo = RectF(
            (1.5 * mPadding).toFloat(),
            convertDpToPixel(6f) + mPadding + mHeight / 3,
            mPadding + convertDpToPixel(9f),
            convertDpToPixel(6f) + mPadding + mHeight / 2
        )
        rectFThree = RectF(
            mPadding + convertDpToPixel(9f),
            convertDpToPixel(3f) + mPadding + mHeight / 3,
            mPadding + convertDpToPixel(18f),
            convertDpToPixel(3f) + mPadding + mHeight / 2
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(rectFOne, 170f, -144f, false, mPaint)

        canvas.drawLine(
            mWidth - convertDpToPixel(3f) - mStrokeWidth,
            mPadding + mHeight / 6,
            mWidth - convertDpToPixel(3f) - mStrokeWidth,
            mHeight - convertDpToPixel(2f) - mHeight / 4,
            mPaint
        )

        canvas.drawLine(
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(8f),
            mPadding + mHeight / 8.5f,
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(8f),
            mHeight - convertDpToPixel(3f) - mHeight / 2.5f,
            mPaint
        )

        canvas.drawLine(
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(17f),
            mPadding + mHeight / 10,
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(17f),
            mHeight - convertDpToPixel(3f) - mHeight / 2.5f,
            mPaint
        )

        canvas.drawLine(
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(26f),
            mPadding + mHeight / 10,
            mWidth - convertDpToPixel(3f) - mStrokeWidth - convertDpToPixel(26f),
            mHeight - convertDpToPixel(2f) - mHeight / 2.5f,
            mPaint
        )

        canvas.drawArc(rectFTwo, 170f, 180f, false, mPaint)
        canvas.drawArc(rectFThree, 175f, -150f, false, mPaint)
    }

    private fun convertDpToPixel(dp: Float): Float {
        val metrics = context.resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
