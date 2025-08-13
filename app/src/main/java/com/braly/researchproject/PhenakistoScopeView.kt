package com.braly.researchproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withSave

class PhenakistoscopeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private var frameCount: Int = 12
    private var currentRotation: Float = 0f
    private var rotateStep: Float = 36f
    private var isAnimating: Boolean = false

    private val FROM_DEGREE = 0f
    private val TO_DEGREE = 360f
    private var imageRadius = 0f
    private var delayMs = 50L
    private var rotateDirection = RotateDirection.CLOCKWISE

    val animationHandler = Handler(android.os.Looper.getMainLooper())

    val animationRunnable = object : Runnable {
        override fun run() {
            println("Value rotate: $currentRotation")
            if (rotateDirection == RotateDirection.COUNTER_CLOCKWISE) {
                currentRotation -= rotateStep
                if (currentRotation <= FROM_DEGREE) {
                    currentRotation = TO_DEGREE
                }
            } else {
                currentRotation += rotateStep
                if (currentRotation >= TO_DEGREE) {
                    currentRotation = FROM_DEGREE
                }
            }
            invalidate()
            animationHandler.postDelayed(this, delayMs)
        }
    }

    // Paint objects
    private val imagePaint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val size = minOf(w, h)
        // image has 80 % size of parent view
        imageRadius = size / 2 * 0.8f
    }

    fun setPhenakistoScopeImage(
        bitmap: Bitmap,
        frameCount: Int,
        rotateDirection: RotateDirection = RotateDirection.CLOCKWISE
    ) {
        this.bitmap = bitmap
        this.frameCount = frameCount
        this.rotateDirection = rotateDirection
        // rotate step to make animation, every draw frame rotate that value
        this.rotateStep = 360f / frameCount
    }

    fun animateStep() {
        if (isAnimating) return
        isAnimating = true
        currentRotation = FROM_DEGREE
        animationHandler.post(animationRunnable)
    }

    fun stopAnimation() {
        isAnimating = false
        animationHandler.removeCallbacks(animationRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let { bmp ->
            val centerX = width / 2f
            val centerY = height / 2f
            canvas.withSave() {
                val scale = (imageRadius * 2) / maxOf(bmp.width, bmp.height)
                rotate(currentRotation, centerX, centerY)
                translate(centerX - (bmp.width * scale / 2), centerY - (bmp.height * scale / 2))
                scale(scale, scale)

                drawBitmap(bmp, 0f, 0f, imagePaint)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }
}