package com.samyak2403.tastytoast


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.facebook.rebound.SimpleSpringListener
import com.facebook.rebound.Spring
import com.facebook.rebound.SpringConfig
import com.facebook.rebound.SpringSystem

object TastyToast {
    const val LENGTH_SHORT = 0
    const val LENGTH_LONG = 1

    const val SUCCESS = 1
    const val WARNING = 2
    const val ERROR = 3
    const val INFO = 4
    const val DEFAULT = 5
    const val CONFUSING = 6

    private var successToastView: SuccessToastView? = null
    private var warningToastView: WarningToastView? = null
    private var errorToastView: ErrorToastView? = null
    private var infoToastView: InfoToastView? = null
    private var defaultToastView: DefaultToastView? = null
    private var confusingToastView: ConfusingToastView? = null

    fun makeText(context: Context, msg: String, length: Int, type: Int): Toast {
        val toast = Toast(context)
        val layoutInflater = LayoutInflater.from(context)

        when (type) {
            SUCCESS -> {
                val layout = layoutInflater.inflate(R.layout.success_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                successToastView = layout.findViewById(R.id.successView)
                successToastView?.startAnim()
                text.setBackgroundResource(R.drawable.success_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
            WARNING -> {
                val layout = layoutInflater.inflate(R.layout.warning_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                warningToastView = layout.findViewById(R.id.warningView)
                val springSystem = SpringSystem.create()
                val spring = springSystem.createSpring().apply {
                    currentValue = 1.8
                    springConfig = SpringConfig(40.0, 5.0)
                    addListener(object : SimpleSpringListener() {
                        override fun onSpringUpdate(spring: Spring) {
                            val value = spring.currentValue.toFloat()
                            val scale = (0.9f - value * 0.5f)
                            warningToastView?.scaleX = scale
                            warningToastView?.scaleY = scale
                        }
                    })
                }
                Thread {
                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        // Handle exception
                    }
                    spring.endValue = 0.4
                }.start()
                text.setBackgroundResource(R.drawable.warning_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
            ERROR -> {
                val layout = layoutInflater.inflate(R.layout.error_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                errorToastView = layout.findViewById(R.id.errorView)
                errorToastView?.startAnim()
                text.setBackgroundResource(R.drawable.error_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
            INFO -> {
                val layout = layoutInflater.inflate(R.layout.info_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                infoToastView = layout.findViewById(R.id.infoView)
                infoToastView?.startAnim()
                text.setBackgroundResource(R.drawable.info_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
            DEFAULT -> {
                val layout = layoutInflater.inflate(R.layout.default_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                defaultToastView = layout.findViewById(R.id.defaultView)
                defaultToastView?.startAnim()
                text.setBackgroundResource(R.drawable.default_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
            CONFUSING -> {
                val layout = layoutInflater.inflate(R.layout.confusing_toast_layout, null, false)
                val text = layout.findViewById<TextView>(R.id.toastMessage)
                text.text = msg
                confusingToastView = layout.findViewById(R.id.confusingView)
                confusingToastView?.startAnim()
                text.setBackgroundResource(R.drawable.confusing_toast)
                text.setTextColor(Color.parseColor("#FFFFFF"))
                toast.view = layout
            }
        }
        toast.duration = length
        toast.show()
        return toast
    }
}
