package com.a7medkenawy.elmarket.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class CustomRadioButton(context: Context, attributeSet: AttributeSet) :
    AppCompatRadioButton(context, attributeSet) {

    init{
        applyFont()
    }
    private fun applyFont() {
        val typeFace = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        typeface = typeFace
    }
}