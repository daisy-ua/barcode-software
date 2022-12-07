package com.daisy.barcode_software.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

open class BarcodePrinter(
    context: Context,
) : View(context) {
    protected open var expectedWidth: Int = width
    protected open var expectedHeight: Int = height

    private var binaryData: String = ""

    fun setBinaryData(binaryData: String) {
        this.binaryData = binaryData
    }

    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 7f
    }

    private val barWidth = 6f
    private val quiteZoneSize = 10
    private val yOffset = 5
    private val barHeight = 300f
    private val quiteZoneWidth: Float = barWidth * quiteZoneSize

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        expectedWidth = width
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val barcodeLength = (binaryData.length + quiteZoneSize + quiteZoneSize) * barWidth

        val xOffset = (expectedWidth - barcodeLength) / 2

        var leftPos = xOffset
        val topPos = yOffset * barWidth
        val bottomPos = top + barHeight

        paint.color = Color.WHITE
        canvas.drawRect(leftPos, topPos, leftPos + quiteZoneWidth, bottomPos, paint)
        leftPos += quiteZoneWidth

        for (c in binaryData) {
            val rightPos = leftPos + barWidth

            paint.color = if (c == '1') Color.BLACK else Color.WHITE

            canvas.drawRect(leftPos, topPos, leftPos + barWidth, bottomPos, paint)

            leftPos = rightPos
        }

        paint.color = Color.WHITE
        canvas.drawRect(leftPos, topPos, leftPos + quiteZoneWidth, bottomPos, paint)

    }
}

@SuppressLint("ViewConstructor")
class BarcodePrinterExtended(
    context: Context,
    binaryData: String,
    private val readable: String,
    override var expectedWidth: Int,
    override var expectedHeight: Int,
) : BarcodePrinter(context) {
    private val yOffset = 20f

    init {
        setBinaryData(binaryData)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()

        paint.apply {
            color = Color.BLACK
            textSize = 45f
            textAlign = Paint.Align.CENTER
        }

        canvas.drawText(readable,
            (expectedWidth / 2).toFloat(),
            expectedHeight.toFloat() - yOffset,
            paint)
    }
}

fun printBarcodeToBitmap(
    context: Context,
    width: Int,
    height: Int,
    binaryData: String,
    readable: String,
): Bitmap? {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val view = BarcodePrinterExtended(context,
        binaryData,
        readable,
        width,
        height
    )

    view.draw(canvas)

    return bitmap
}