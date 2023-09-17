package com.ourproject.component.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ourproject.component.databinding.HeaderViewBinding

class HeaderWithTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {


    private val binding: HeaderViewBinding =
        HeaderViewBinding.inflate(LayoutInflater.from(context), this, true)


    var insertTitle = ""
        set(value) {
            field = value
            binding.headerTitle.text = value
        }

    var insertSubtitle = ""
        set(value) {
            field = value
            binding.headerSubtitle.text = value
        }

    init {
        binding.root
    }
}