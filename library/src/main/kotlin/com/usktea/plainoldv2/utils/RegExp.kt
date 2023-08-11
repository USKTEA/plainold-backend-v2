package com.usktea.plainoldv2.utils

enum class RegExp(
    val value: String
) {
    EMAIL("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"),
    USERNAME("^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"),
    NAME("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,}$"),
    PHONENUMBER("^[0-9]{3}-[0-9]{3,4}-[0-9]{4}"),
    ZIPCODE("^\\d{3}-?\\d{3}$"),
}
