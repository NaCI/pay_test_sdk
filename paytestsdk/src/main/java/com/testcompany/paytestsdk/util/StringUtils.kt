package com.testcompany.paytestsdk.util

object StringUtils {
    const val EMPTY = ""
    const val SPACE = " "
    const val PLUS = "+"
    const val COMMA = ","
    const val NEW_LINE = "\n"
    const val SLASH_WITH_SPACE = " / "
    const val DELIMITER_DEVICE_INFO = "-"
    const val DELIMITER_DEVICE_INFO_WITH_SPACE = " - "
    const val TAG_BOLD = "<b>"
    const val TAG_BOLD_CLOSE = "</b>"

    private fun StringUtils() {
        //no instance
    }

    fun isEmpty(str: String?): Boolean {
        return str == null || str.isEmpty()
    }
}