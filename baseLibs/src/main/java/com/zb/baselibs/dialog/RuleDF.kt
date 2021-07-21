package com.zb.baselibs.dialog

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.zb.baselibs.R
import com.zb.baselibs.app.BaseApp
import com.zb.baselibs.databinding.DfRuleBinding

class RuleDF(activity: AppCompatActivity) : BaseDialogFragment(activity, false, false) {
    private lateinit var binding: DfRuleBinding
    private lateinit var callBack: CallBack
    private var content: String = ""
    private var registerRule: String = ""
    private var privacyRule: String = ""

    override val layoutId: Int
        get() = R.layout.df_rule

    override fun setDataBinding(viewDataBinding: ViewDataBinding?) {
        binding = viewDataBinding as DfRuleBinding
    }

    fun show(manager: FragmentManager?) {
        show(manager!!, "${BaseApp.projectName}_RuleDF")
    }

    fun setContent(content: String): RuleDF {
        this.content = content
        return this
    }

    fun setRegisterRule(registerRule: String): RuleDF {
        this.registerRule = registerRule
        return this
    }

    fun setPrivacyRule(privacyRule: String): RuleDF {
        this.privacyRule = privacyRule
        return this
    }

    override fun onStart() {
        super.onStart()
        center(0.8)
    }

    fun setCallBack(callBack: CallBack): RuleDF {
        this.callBack = callBack
        return this
    }

    override fun initUI() {
        binding.dialog = this

        val rule1Start = content.indexOf(registerRule)
        val rule1End = rule1Start + registerRule.length
        val rule2Start = content.indexOf(privacyRule)
        val rule2End = rule2Start + privacyRule.length
        val rule1StartLast = content.lastIndexOf(registerRule)
        val rule1EndLast = rule1StartLast + registerRule.length
        val rule2StartLast = content.lastIndexOf(privacyRule)
        val rule2EndLast = rule2StartLast + privacyRule.length

        val style = SpannableString(content)

        style.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                callBack.registerUrlBack()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, rule1Start, rule1End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                callBack.privacyUrlBack()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, rule2Start, rule2End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                callBack.registerUrlBack()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, rule1StartLast, rule1EndLast, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {
                callBack.privacyUrlBack()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }, rule2StartLast, rule2EndLast, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        style.setSpan(
            ForegroundColorSpan(Color.parseColor("#0d88c1")),
            rule1Start, rule1End,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        style.setSpan(
            ForegroundColorSpan(Color.parseColor("#0d88c1")),
            rule2Start, rule2End,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        style.setSpan(
            ForegroundColorSpan(Color.parseColor("#0d88c1")),
            rule1StartLast, rule1EndLast,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        style.setSpan(
            ForegroundColorSpan(Color.parseColor("#0d88c1")),
            rule2StartLast, rule2EndLast,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvContent.text = style
        binding.tvContent.movementMethod = LinkMovementMethod.getInstance()
    }


    fun cancel(view: View) {
        callBack.cancel()
        dismiss()
    }

    fun sure(view: View) {
        callBack.sure()
        dismiss()
    }


    interface CallBack {
        fun sure()
        fun cancel()
        fun registerUrlBack()
        fun privacyUrlBack()

    }
}