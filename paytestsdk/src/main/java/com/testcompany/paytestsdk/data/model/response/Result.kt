package com.testcompany.paytestsdk.data.model.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.data.model.type.ResultCode

data class Result(
    @field:SerializedName("Result")
    var result: JsonElement? = null,
    @field:SerializedName("ResultCode")
    var resultCode: Int = 0,
    @field:SerializedName("ResultMessage")
    var resultMessage: String? = null
) : BaseResponse {
    open fun isSuccess(): Boolean {
        return resultCode == ResultCode.SUCCESS.value
    }
}