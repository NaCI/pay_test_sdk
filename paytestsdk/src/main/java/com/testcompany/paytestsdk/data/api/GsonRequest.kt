package com.testcompany.paytestsdk.data.api

import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.JsonSyntaxException
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.api.NetworkManager.Companion.DEFAULT_TIMEOUT_MILLIS
import com.testcompany.paytestsdk.data.error.VolleyParseError
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

internal class GsonRequest<I : BaseRequest, O : BaseResponse>(
    private val requestUrl: String,
    private val requestClass: I,
    private val responseClass: Class<O>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<O>,
    errorListener: Response.ErrorListener
) : JsonRequest<O>(
    Method.POST,
    requestUrl,
    PayTest.getComponent().gson().toJson(requestClass),
    listener,
    errorListener
) {

    init {
        this.retryPolicy = DefaultRetryPolicy(DEFAULT_TIMEOUT_MILLIS, 1, 0.0f)
    }

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: O) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<O> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
//            val baseResponse = gson.fromJson(json, Result::class.java)
//            val model = gson.fromJson(baseResponse.result, responseClass)

            val model = PayTest.getComponent().gson().fromJson(json, responseClass)
            Response.success(
                model,
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(VolleyParseError(response))
        } catch (e: JsonSyntaxException) {
            Response.error(VolleyParseError(response))
        }
    }

    override fun toString(): String {
        return "GsonRequest(requestUrl='$requestUrl', requestClass=$requestClass, responseClass=$responseClass, headers=$headers)"
    }


}
