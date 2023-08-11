package com.usktea.plainoldv2.utils

import org.springframework.stereotype.Component

@Component
class RegExpProvider {
    companion object {
        fun email(): Regex {
            return Regex(RegExp.EMAIL.value)
        }

        fun username(): Regex {
            return Regex(RegExp.USERNAME.value)
        }

        fun name(): Regex {
            return Regex(RegExp.NAME.value)
        }

        fun phoneNumber(): Regex {
            return Regex(RegExp.PHONENUMBER.value)
        }

        fun zipCode(): Regex {
            return Regex(RegExp.ZIPCODE.value)
        }
    }
}
