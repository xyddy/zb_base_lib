package com.zb.baselibs.dialog

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.zb.baselibs.R
import com.zb.baselibs.adapter.SelectAdapter
import com.zb.baselibs.app.BaseApp
import com.zb.baselibs.databinding.DfBirthdayBinding
import com.zb.baselibs.utils.DateUtil
import org.jaaksi.pickerview.widget.DefaultCenterDecoration

class BirthdayDF(activity: AppCompatActivity) : BaseDialogFragment(activity, false, false) {

    private lateinit var binding: DfBirthdayBinding
    private var birthday: String = ""
    private lateinit var callBack: CallBack
    private var maxYear: Int = 0
    private var minYear: Int = 0
    private var yearList = ArrayList<String>()
    private var monthList = ArrayList<String>()
    private var dayList = ArrayList<String>()
    private var mYear = 0
    private var mMonth = 0
    private var mDay = 0
    private var mYearPosition = 0
    private var mMonthPosition = 0
    private var mDayPosition = 0
    private lateinit var decoration: DefaultCenterDecoration

    override val layoutId: Int
        get() = R.layout.df_birthday

    override fun setDataBinding(viewDataBinding: ViewDataBinding?) {
        binding = viewDataBinding as DfBirthdayBinding
    }

    fun show(manager: FragmentManager?) {
        show(manager!!, "${BaseApp.projectName}_BirthdayDF")
    }

    fun setBirthday(birthday: String): BirthdayDF {
        this.birthday = birthday
        return this
    }

    fun setMaxYear(maxYear: Int): BirthdayDF {
        this.maxYear = maxYear
        return this
    }

    fun setMinYear(minYear: Int): BirthdayDF {
        this.minYear = minYear
        return this
    }

    fun setCallBack(callBack: CallBack): BirthdayDF {
        this.callBack = callBack
        return this
    }

    override fun initUI() {
        binding.dialog = this
        if (birthday.isEmpty()) {
            birthday = "${maxYear}-01-01"
        }
        val dates = birthday.split("-".toRegex()).toTypedArray()
        mYear = dates[0].toInt()
        mMonth = dates[1].toInt()
        mDay = dates[2].toInt()

        decoration = DefaultCenterDecoration(context)
        decoration.setLineColor(Color.parseColor("#eeeeee"))
        decoration.setLineWidth(0.1f)

        binding.yearPv.visibleItemCount = 5
        binding.monthPv.visibleItemCount = 5
        binding.dayPv.visibleItemCount = 5

        setYearList()
        setMonthList()
        setDayList()
    }

    private fun setYearList() {
        for (i in minYear..maxYear) {
            yearList.add("${i}年")
        }
        mYearPosition = yearList.indexOf("${mYear}年")
        binding.yearPv.adapter = SelectAdapter(yearList)
        binding.yearPv.setCenterDecoration(decoration)
        binding.yearPv.selectedPosition = mYearPosition
        binding.yearPv.setOnSelectedListener { pickerView, position ->
            this.mYearPosition = position
            mYear = yearList[position].replace("年", "").toInt()
            setDayList()
        }
    }

    private fun setMonthList() {
        for (i in 1..12) {
            monthList.add("${i}月")
        }
        mMonthPosition = monthList.indexOf("${mMonth}月")

        binding.monthPv.adapter = SelectAdapter(monthList)
        binding.monthPv.setCenterDecoration(decoration)
        binding.monthPv.selectedPosition = mMonthPosition
        binding.monthPv.setOnSelectedListener { pickerView, position ->
            this.mMonthPosition = position
            mMonth = monthList[position].replace("月", "").toInt()
            setDayList()
        }
    }

    private fun setDayList() {
        val days = DateUtil.getDaysByYearMonth(mYear, mMonth)
        dayList.clear()
        for (i in 1..days) {
            dayList.add("${i}日")
        }
        mDayPosition =
            if (dayList.indexOf("${mDay}日") == -1) dayList.size - 1 else dayList.indexOf("${mDay}日")

        binding.dayPv.adapter = SelectAdapter(dayList)
        binding.dayPv.setCenterDecoration(decoration)
        binding.dayPv.selectedPosition = mDayPosition
        binding.dayPv.setOnSelectedListener { pickerView, position ->
            this.mDayPosition = position
            mDay = dayList[position].replace("日", "").toInt()
        }
    }

    fun cancel(view: View) {
        dismiss()
    }

    fun sure(view: View) {
        callBack.sure("${mYear}-${if (mMonth < 10) "0${mMonth}" else "$mMonth"}-${if (mDay < 10) "0${mDay}" else "$mDay"}")
        dismiss()
    }

    interface CallBack {
        fun sure(birthday: String)
    }
}