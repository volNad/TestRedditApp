package com.example.testredditapp.utils

import java.util.concurrent.TimeUnit

class ConverterHelpers {

    companion object {
        fun timeToNormal(epochTimestamp: Long): String {

            val currentTimeMillis = System.currentTimeMillis()
            val timeDifferenceMillis = currentTimeMillis - (epochTimestamp * 1000)

            val hoursDifference = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis)

            return if (hoursDifference < 1) {

                val minutesDifference = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis)
                "$minutesDifference minutes ago"
            } else {"$hoursDifference hours ago"}
        }

    }
}