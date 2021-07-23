package com.zb.baselibs.bean

data class VipInfo(
    var memberOfOpenedProductId: Long = 0, var title: String? = "", var originalPrice: Double = 0.0, var price: Double = 0.0,
    var priceDesc: String? = "", var dayCount: Int = 0, var image: String? = "", var simpleDesc: String? = ""
)
