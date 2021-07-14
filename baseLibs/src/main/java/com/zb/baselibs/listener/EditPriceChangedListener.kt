package com.zb.baselibs.listener

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * 价格输入框
 */
class EditPriceChangedListener(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {
        var s = editable.toString()
        if (s.contains(".")) {
            if (s.length - 1 - s.indexOf(".") > 2) {
                s = s.substring(0, s.indexOf(".") + (2 + 1))
                editText.setText(s)
                editText.setSelection(s.length)
            }
        }
        //限制只能输入一次小数点
        if (editText.text.toString().contains(".")) {
            if (editText.text.toString()
                    .indexOf(".", editText.text.toString().indexOf(".") + 1) > 0
            ) {
                editText.setText(
                    editText.text.toString().substring(0, editText.text.toString().length - 1)
                )
                editText.setSelection(editText.text.toString().length)
            }
        }

        //第一次输入为点的时候
        if (s.trim { it <= ' ' } == ".") {
            s = "0$s"
            editText.setText(s)
            editText.setSelection(2)
        }

        //个位数为0的时候
        if (s.startsWith("0") && s.trim { it <= ' ' }.length > 1) {
            if (s.substring(1, 2) != ".") {
                editText.setText(s.subSequence(0, 1))
                editText.setSelection(1)
            }
        }
    }
}