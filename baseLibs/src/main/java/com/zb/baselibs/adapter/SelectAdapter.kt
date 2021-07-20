package com.zb.baselibs.adapter

import org.jaaksi.pickerview.adapter.WheelAdapter

class SelectAdapter(var data: ArrayList<String>) : WheelAdapter<String> {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItem(index: Int): String {
        return data[index]
    }
}