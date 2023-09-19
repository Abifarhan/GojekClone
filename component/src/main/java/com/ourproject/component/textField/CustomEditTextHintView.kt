package com.ourproject.component.textField

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ourproject.component.databinding.CustumEditTextBinding

class CustomEditTextHintView(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    private val binding: CustumEditTextBinding =
        CustumEditTextBinding.inflate(LayoutInflater.from(context), this, true)


    fun setHint(hint: String){
        binding.hintTextView.text = hint
    }


    fun getText(): String {
        return binding.editText.text.toString()
    }


    init {
        binding.root
    }
}