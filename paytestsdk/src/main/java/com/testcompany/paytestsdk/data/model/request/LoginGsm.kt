package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginGsm(
    @field:SerializedName("loginInfo")
    var loginInfoGsm: LoginInfoGsm
) : BaseRequest(), Parcelable