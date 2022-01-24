package com.a7medkenawy.elmarket.utils.customviews

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomEditText(context: Context, attributes: AttributeSet) :
    AppCompatTextView(context, attributes) {


    init {
        applyFont()
    }

    private fun applyFont() {
        val typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)

    }

}