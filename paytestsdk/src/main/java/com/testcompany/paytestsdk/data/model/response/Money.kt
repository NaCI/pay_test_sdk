package com.testcompany.paytestsdk.data.model.response

import android.os.Build
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.util.Formatter
import com.testcompany.paytestsdk.util.StringUtils
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
data class Money(
    @SerializedName("Money")
    var money: Double = 0.0,

    @SerializedName("Currency")
    var currency: String? = null,

    @SerializedName("Sign")
    var sign: String? = null,

    @SerializedName("DisplayValue")
    private var displayValue: String? = null,

    @SerializedName("IsCountable")
    var isCountable: Int = 0

) : Comparable<Money>, BaseResponse, Parcelable {

    init {
        val moneyDouble: Double = money / Formatter.MONEY_DIVISION
        val builder = StringBuilder()
        if (sign.isNullOrEmpty()) {
            builder.append(String.format("%.2f", moneyDouble))
            builder.append(StringUtils.EMPTY)
            builder.append(currency)
        } else {
            builder.append(sign)
            builder.append(String.format("%.2f", moneyDouble))
        }
        this.displayValue = builder.toString()
    }

    fun hasCountableBalance(): Boolean {
        return isCountable == 0
    }

    fun getDisplayValue(): String? {
        val countable = isCountable == 1
        return if (countable) {
            displayValue
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            for (i in 0 until sign!!.length) {
                val c = sign!![i]
                if (!Character.isDefined(c)) {
                    break
                }
            }
            displayValue
        } else {
            val formatter = DecimalFormat("#,##0.00")
            val value = formatter.format(money / 100)
            val builder = java.lang.StringBuilder()
            builder.append(value)
            builder.append(StringUtils.SPACE)
            builder.append(currency)
            builder.toString()
        }
    }

    fun setDisplayValue(displayValue: String?) {
        this.displayValue = displayValue
    }

    fun isEmpty(): Boolean {
        return money == 0.0
    }

    override fun compareTo(other: Money): Int {
        return (this.money - other.money).toInt()
    }

    fun isLessThan(money: Money?): Boolean {
        return this < money!!
    }

    fun isLessThanOrEqual(money: Money?): Boolean {
        return this <= money!!
    }
}